package aspr.asprandalliums.blocks;

import com.umpaz.nethers_delight.core.util.NDTextUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import vectorwing.farmersdelight.registry.ModSounds;
import vectorwing.farmersdelight.utils.TextUtils;
import vectorwing.farmersdelight.utils.tags.ModTags;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LargePorkFeast extends HorizontalBlock {

    public static final EnumProperty<BedPart> PART;
    public static final IntegerProperty SERVINGS;
    public final Supplier<Item> sliceItem;
    public final Supplier<Item> firstServingItem;
    public final Supplier<Item> secondServingItem;
    protected static final VoxelShape[] HEAD_SHAPES;
    protected static final VoxelShape[] FOOT_SHAPES;
    protected static final VoxelShape FOOT_BOWL;


    public LargePorkFeast(Properties properties, Supplier<Item> sliceItem, Supplier<Item> secondServingItem, Supplier<Item> firstServingItem) {
        super(properties);
        this.sliceItem = sliceItem;
        this.firstServingItem = firstServingItem;
        this.secondServingItem = secondServingItem;
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.SOUTH)).with(SERVINGS, 10)).with(PART, BedPart.HEAD));
    }

    public ItemStack getKnifeSliceItem() {
        return new ItemStack((IItemProvider)this.sliceItem.get());
    }

    public ItemStack getFirstServingItem() {
        return new ItemStack((IItemProvider)this.firstServingItem.get());
    }
    public ItemStack getSecondServingItem() {
        return new ItemStack((IItemProvider)this.secondServingItem.get());
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int servings = (Integer)state.get(SERVINGS);
        ItemStack servingOne = this.getFirstServingItem();
        ItemStack servingTwo = this.getSecondServingItem();
        ItemStack heldItem = player.getHeldItem(handIn);
        if (servings == 0) {
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            worldIn.destroyBlock(pos, true);
            return ActionResultType.SUCCESS;
        } else {
            if (servings > 7) {
                if (heldItem.isItemEqual(servingOne.getContainerItem())) {
                    return this.takePork(worldIn, pos, state, player, handIn);
                }

                player.sendStatusMessage(TextUtils.getTranslation("block.feast.use_container", new Object[]{servingOne.getContainerItem().getDisplayName()}), true);
            }

            if (servings < 8 && servings > 4) {

                    return this.takeFry(worldIn, pos, state, player, handIn);

            }

            if (servings < 5) {
                if (ModTags.KNIVES.contains(heldItem.getItem())) {
                    return this.cutRib(worldIn, pos, state, heldItem, player);
                }

                player.sendStatusMessage(NDTextUtils.getTranslation("block.feast.use_knife"), true);
            }

            return ActionResultType.SUCCESS;
        }
    }

    public ActionResultType cutRib(World worldIn, BlockPos pos, BlockState state, ItemStack tool, @Nullable PlayerEntity player) {
        int servings = (Integer)state.get(SERVINGS);
        BedPart part = (BedPart)state.get(PART);
        BlockPos pairPos = pos.offset(getDirectionToOtherPart(part, (Direction)state.get(HORIZONTAL_FACING)));
        BlockState pairState = worldIn.getBlockState(pairPos);
        worldIn.setBlockState(pairPos, (BlockState)pairState.with(SERVINGS, servings - 1), 3);
        worldIn.setBlockState(pos, (BlockState)state.with(SERVINGS, servings - 1), 3);
        InventoryHelper.spawnItemStack(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), this.getKnifeSliceItem());
        worldIn.playSound((PlayerEntity)null, pos, (SoundEvent) ModSounds.BLOCK_CUTTING_BOARD_KNIFE.get(), SoundCategory.PLAYERS, 0.8F, 0.8F);
        if (player != null) {
            tool.damageItem(1, player, (user) -> {
                user.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        } else if (tool.attemptDamageItem(1, worldIn.rand, (ServerPlayerEntity)null)) {
            tool.setCount(0);
        }

        return ActionResultType.SUCCESS;
    }

    public ActionResultType takePork(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand handIn) {
        int servings = (Integer)state.get(SERVINGS);
        BedPart part = (BedPart)state.get(PART);
        BlockPos pairPos = pos.offset(getDirectionToOtherPart(part, (Direction)state.get(HORIZONTAL_FACING)));
        BlockState pairState = worldIn.getBlockState(pairPos);
        ItemStack serving = this.getFirstServingItem();
        ItemStack heldItem = player.getHeldItem(handIn);
        worldIn.setBlockState(pairPos, (BlockState)pairState.with(SERVINGS, servings - 1), 3);
        worldIn.setBlockState(pos, (BlockState)state.with(SERVINGS, servings - 1), 3);
        if (!player.abilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        if (!player.inventory.addItemStackToInventory(serving)) {
            player.dropItem(serving, false);
        }

        worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return ActionResultType.SUCCESS;
    }

    public ActionResultType takeFry(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand handIn) {
        int servings = (Integer)state.get(SERVINGS);
        BedPart part = (BedPart)state.get(PART);
        ItemStack serving = this.getSecondServingItem();
        BlockPos pairPos = pos.offset(getDirectionToOtherPart(part, (Direction)state.get(HORIZONTAL_FACING)));
        BlockState pairState = worldIn.getBlockState(pairPos);
        worldIn.setBlockState(pairPos, (BlockState)pairState.with(SERVINGS, servings - 1), 3);
        worldIn.setBlockState(pos, (BlockState)state.with(SERVINGS, servings - 1), 3);

        if (!player.inventory.addItemStackToInventory(serving)) {
            player.dropItem(serving, false);
        }

        worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return ActionResultType.SUCCESS;
    }


    public static Direction getDirectionToOtherPart(BedPart part, Direction direction) {
        return part == BedPart.HEAD ? direction : direction.getOpposite();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HORIZONTAL_FACING, SERVINGS, PART});
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(PART) == BedPart.HEAD) {
            if (state.get(HORIZONTAL_FACING) == Direction.NORTH) {

                return HEAD_SHAPES[(Integer)state.get(SERVINGS)];

            }
            if (state.get(HORIZONTAL_FACING) == Direction.EAST) {

                return rotateShape(Direction.NORTH, Direction.EAST, HEAD_SHAPES[(Integer)state.get(SERVINGS)]);

            }
            if (state.get(HORIZONTAL_FACING) == Direction.SOUTH) {

                return rotateShape(Direction.NORTH, Direction.SOUTH, HEAD_SHAPES[(Integer)state.get(SERVINGS)]);

            }
            else {

                return rotateShape(Direction.NORTH, Direction.WEST, HEAD_SHAPES[(Integer)state.get(SERVINGS)]);

            }

        }
        else {
            if (state.get(HORIZONTAL_FACING) == Direction.NORTH) {

                return FOOT_SHAPES[(Integer)state.get(SERVINGS)];

            }
            if (state.get(HORIZONTAL_FACING) == Direction.EAST) {

                return rotateShape(Direction.NORTH, Direction.EAST, FOOT_SHAPES[(Integer)state.get(SERVINGS)]);

            }
            if (state.get(HORIZONTAL_FACING) == Direction.SOUTH) {

                return rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[(Integer)state.get(SERVINGS)]);

            }
            else {

                return rotateShape(Direction.NORTH, Direction.WEST, FOOT_SHAPES[(Integer)state.get(SERVINGS)]);
            }
        }
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing != getDirectionToOtherPart((BedPart)stateIn.get(PART), (Direction)stateIn.get(HORIZONTAL_FACING))) {
            return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return stateIn.isValidPosition(worldIn, currentPos) && facingState.matchesBlock(this) && facingState.get(PART) != stateIn.get(PART) ? stateIn : Blocks.AIR.getDefaultState();
        }
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return true;
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && player.isCreative()) {
            BedPart part = (BedPart)state.get(PART);
            if (part == BedPart.FOOT) {
                BlockPos pairPos = pos.offset(getDirectionToOtherPart(part, (Direction)state.get(HORIZONTAL_FACING)));
                BlockState pairState = worldIn.getBlockState(pairPos);
                if (pairState.getBlock() == this && pairState.get(PART) == BedPart.HEAD) {
                    worldIn.setBlockState(pairPos, Blocks.AIR.getDefaultState(), 35);
                    worldIn.playEvent(player, 2001, pairPos, Block.getStateId(pairState));
                }
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };

        int times = (to.getHorizontalIndex() - from.getHorizontalIndex() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            BlockPos facingPos = pos.offset((Direction)state.get(HORIZONTAL_FACING));
            worldIn.setBlockState(facingPos, (BlockState)state.with(PART, BedPart.FOOT), 3);
            worldIn.updateBlock(pos, Blocks.AIR);
            state.updateNeighbours(worldIn, pos, 3);
        }

    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getPlacementHorizontalFacing();
        BlockPos pos = context.getPos();
        BlockPos pairPos = pos.offset(facing);
        return context.getWorld().getBlockState(pairPos).isReplaceable(context) ? (BlockState)this.getDefaultState().with(HORIZONTAL_FACING, facing) : null;
    }

    static {

        PART = BlockStateProperties.BED_PART;
        SERVINGS = IntegerProperty.create("servings", 0, 10);
        FOOT_BOWL = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(
                1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 16.0D), Block.makeCuboidShape(
                2.0D, 2.0D, 2.0D, 14.0D, 1.0D, 16.0D), IBooleanFunction.ONLY_FIRST);
        FOOT_SHAPES = new VoxelShape[]{
                FOOT_BOWL,
                FOOT_BOWL,
                FOOT_BOWL,
                VoxelShapes.combine(FOOT_BOWL,
                        Block.makeCuboidShape(5.0D, 1.0D, 12.0D, 11.0D, 9.0D, 16.0D)
                        ,IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL,
                        Block.makeCuboidShape(5.0D, 1.0D, 8.0D, 11.0D, 9.0D, 16.0D)
                        ,IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 2.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 2.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                ), IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 3.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 3.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                ), IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 4.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 4.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                ), IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 4.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 4.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                ), IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 4.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 4.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                ), IBooleanFunction.OR),
                VoxelShapes.combine(FOOT_BOWL, VoxelShapes.combine(
                        Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 4.0D, 16.0D),
                        Block.makeCuboidShape(5.0D, 4.0D, 8.0D, 11.0D, 9.0D, 16.0D),
                        IBooleanFunction.OR
                    ), IBooleanFunction.OR)
                };
        HEAD_SHAPES =new VoxelShape[]{
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[0]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[1]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[2]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[3]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[4]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[5]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[6]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[7]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[8]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[9]),
                rotateShape(Direction.NORTH, Direction.SOUTH, FOOT_SHAPES[10])
            };
    }
}


