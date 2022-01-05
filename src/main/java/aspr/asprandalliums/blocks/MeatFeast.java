package aspr.asprandalliums.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import vectorwing.farmersdelight.blocks.FeastBlock;

import java.util.function.Supplier;

public class MeatFeast extends FeastBlock {

    protected static final VoxelShape BOWL_SHAPE = VoxelShapes.combineAndSimplify( Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D),
            Block.makeCuboidShape(2.0D,1.0D,2.0D,14.0D,6.0D,14.0D), IBooleanFunction.ONLY_FIRST);
    protected static final VoxelShape ROAST_SHAPE = VoxelShapes.combine(BOWL_SHAPE, Block.makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 12.0D, 14.0D), IBooleanFunction.OR);

    public MeatFeast(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return (Integer)state.get(SERVINGS) == 0 ? BOWL_SHAPE : ROAST_SHAPE;
    }

}
