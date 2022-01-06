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
    public final Supplier<Item> servingItem;
    protected static final VoxelShape[] SHAPES;

    public LargePorkFeast(Properties properties, Supplier<Item> sliceItem, Supplier<Item> servingItem) {
        super(properties);
        this.sliceItem = sliceItem;
        this.servingItem = servingItem;
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.SOUTH)).with(SERVINGS, 10)).with(PART, BedPart.HEAD));
    }

    public ItemStack getKnifeSliceItem() {
        return new ItemStack((IItemProvider)this.sliceItem.get());
    }

    public ItemStack getServingItem() {
        return new ItemStack((IItemProvider)this.servingItem.get());
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int servings = (Integer)state.get(SERVINGS);
        ItemStack serving = this.getServingItem();
        ItemStack heldItem = player.getHeldItem(handIn);
        if (servings == 0) {
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            worldIn.destroyBlock(pos, true);
            return ActionResultType.SUCCESS;
        } else {
            if (servings > 4) {
                if (heldItem.isItemEqual(serving.getContainerItem())) {
                    return this.takePork(worldIn, pos, state, player, handIn);
                }

                player.sendStatusMessage(TextUtils.getTranslation("block.feast.use_container", new Object[]{serving.getContainerItem().getDisplayName()}), true);
            }

            if (servings < 5) {
                if (ModTags.KNIVES.contains(heldItem.getItem())) {
                    return this.cutRib(worldIn, pos, state, heldItem, player);
                }

                player.sendStatusMessage(NDTextUtils.getTranslation("block.feast.use_knife", new Object[]{serving.getContainerItem().getDisplayName()}), true);
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
        ItemStack serving = this.getServingItem();
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


    public static Direction getDirectionToOtherPart(BedPart part, Direction direction) {
        return part == BedPart.HEAD ? direction : direction.getOpposite();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HORIZONTAL_FACING, SERVINGS, PART});
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[(Integer)state.get(SERVINGS)];
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
        SHAPES = new VoxelShape[]{
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
                Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D)
        };
    }

}
