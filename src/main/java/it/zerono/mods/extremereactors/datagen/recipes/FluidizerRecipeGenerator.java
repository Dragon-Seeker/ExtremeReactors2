/*
 *
 * FluidizerRecipeGenerator.java
 *
 * This file is part of Extreme Reactors 2 by ZeroNoRyouki, a Minecraft mod.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * DO NOT REMOVE OR EDIT THIS HEADER
 *
 */

package it.zerono.mods.extremereactors.datagen.recipes;

import it.zerono.mods.extremereactors.ExtremeReactors;
import it.zerono.mods.extremereactors.api.reactor.ReactantMappingsRegistry;
import it.zerono.mods.extremereactors.gamecontent.Content;
import it.zerono.mods.extremereactors.gamecontent.ContentTags;
import it.zerono.mods.extremereactors.gamecontent.fluid.ReactantFluid;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerFluidMixingRecipe;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerSolidMixingRecipe;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerSolidRecipe;
import it.zerono.mods.zerocore.lib.recipe.ingredient.FluidStackRecipeIngredient;
import it.zerono.mods.zerocore.lib.recipe.ingredient.ItemStackRecipeIngredient;
import it.zerono.mods.zerocore.lib.recipe.result.FluidStackRecipeResult;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluidizerRecipeGenerator
        extends AbstractRecipeGenerator {

    public FluidizerRecipeGenerator(final DataGenerator generatorIn) {
        super(generatorIn);
    }

    //region RecipeProvider

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return ExtremeReactors.MOD_NAME + "Fluidizer recipes";
    }

    /**
     * Registers all recipes to the given consumer.
     */
    @Override
    protected void buildShapelessRecipes(final Consumer<IFinishedRecipe> c) {

        // machine recipes

        solid(c, "yellorium", Content.Items.YELLORIUM_INGOT, Content.Fluids.YELLORIUM_SOURCE);
        solid(c, "yellorium9", Content.Items.YELLORIUM_BLOCK, Content.Fluids.YELLORIUM_SOURCE, 9);
        solid(c, "cyanite", Content.Items.CYANITE_INGOT, Content.Fluids.CYANITE_SOURCE);
        solid(c, "cyanite9", Content.Items.CYANITE_BLOCK, Content.Fluids.CYANITE_SOURCE, 9);
        solid(c, "blutonium", Content.Items.BLUTONIUM_INGOT, Content.Fluids.BLUTONIUM_SOURCE);
        solid(c, "blutonium9", Content.Items.BLUTONIUM_BLOCK, Content.Fluids.BLUTONIUM_SOURCE, 9);
        solid(c, "magentite", Content.Items.MAGENTITE_INGOT, Content.Fluids.MAGENTITE_SOURCE);
        solid(c, "magentite9", Content.Items.MAGENTITE_BLOCK, Content.Fluids.MAGENTITE_SOURCE, 9);
        solidMixing(c, "verderium", Content.Items.YELLORIUM_INGOT, 2, Content.Items.BLUTONIUM_INGOT, 1, Content.Fluids.VERDERIUM_SOURCE, 2);
        solidMixing(c, "verderium9", Content.Items.YELLORIUM_BLOCK, 2, Content.Items.BLUTONIUM_BLOCK, 1, Content.Fluids.VERDERIUM_SOURCE, 18);
        fluidMixing(c, "verderium", Content.Fluids.YELLORIUM_SOURCE, 2000, Content.Fluids.BLUTONIUM_SOURCE, 1000, Content.Fluids.VERDERIUM_SOURCE, 2000);

        // fluidizer blocks

        this.casing(c);
        this.glass(c);
        this.controller(c);
        this.port(c, "solidinjector", Content.Items.FLUIDIZER_SOLIDINJECTOR, Items.STICKY_PISTON,
                ContentTags.Items.INGOTS_YELLORIUM, Tags.Items.DUSTS_REDSTONE);
        this.port(c, "fluidinjector", Content.Items.FLUIDIZER_FLUIDINJECTOR, Items.PISTON,
                Tags.Items.INGOTS_NETHERITE, Tags.Items.GEMS_LAPIS);
        this.port(c, "outputport", Content.Items.FLUIDIZER_OUTPUTPORT, Items.DISPENSER,
                Tags.Items.STORAGE_BLOCKS_LAPIS, Tags.Items.CHESTS);
        this.port(c, "powerport", Content.Items.FLUIDIZER_POWERPORT, Items.REPEATER,
                Tags.Items.STORAGE_BLOCKS_REDSTONE, Tags.Items.GEMS_DIAMOND);
    }

    //endregion
    //region internals

    private static void solid(final Consumer<IFinishedRecipe> c, final String name,
                              final Supplier<? extends Item> ingredient, final Supplier<ReactantFluid.Source> result) {
        solid(c, name, ingredient, result, 1);
    }

    private static void solid(final Consumer<IFinishedRecipe> c, final String name,
                              final Supplier<? extends Item> ingredient, final Supplier<ReactantFluid.Source> result,
                              final int resultMultiplier) {

        FluidizerSolidRecipe.builder(ItemStackRecipeIngredient.from(ingredient.get()),
                        FluidStackRecipeResult.from(new FluidStack(result.get(), ReactantMappingsRegistry.STANDARD_SOLID_REACTANT_AMOUNT * resultMultiplier)))
                .build(c, ExtremeReactors.newID("fluidizer/solid/" + name));
    }

    private static void solidMixing(final Consumer<IFinishedRecipe> c, final String name,
                                    final Supplier<? extends Item> ingredient1, final int ingredient1Amount,
                                    final Supplier<? extends Item> ingredient2, final int ingredient2Amount,
                                    final Supplier<ReactantFluid.Source> result, final int resultMultiplier) {

        FluidizerSolidMixingRecipe.builder(ItemStackRecipeIngredient.from(ingredient1.get(), ingredient1Amount),
                        ItemStackRecipeIngredient.from(ingredient2.get(), ingredient2Amount),
                        FluidStackRecipeResult.from(new FluidStack(result.get(), ReactantMappingsRegistry.STANDARD_SOLID_REACTANT_AMOUNT * resultMultiplier)))
                .build(c, ExtremeReactors.newID("fluidizer/solidmixing/" + name + "_1"));

        FluidizerSolidMixingRecipe.builder(ItemStackRecipeIngredient.from(ingredient2.get(), ingredient2Amount),
                        ItemStackRecipeIngredient.from(ingredient1.get(), ingredient1Amount),
                        FluidStackRecipeResult.from(new FluidStack(result.get(), ReactantMappingsRegistry.STANDARD_SOLID_REACTANT_AMOUNT * resultMultiplier)))
                .build(c, ExtremeReactors.newID("fluidizer/solidmixing/" + name + "_2"));
    }

    private static void fluidMixing(final Consumer<IFinishedRecipe> c, final String name,
                                    final Supplier<ReactantFluid.Source> ingredient1, final int ingredient1Amount,
                                    final Supplier<ReactantFluid.Source> ingredient2, final int ingredient2Amount,
                                    final Supplier<ReactantFluid.Source> result, final int resultAmount) {

        FluidizerFluidMixingRecipe.builder(FluidStackRecipeIngredient.from(ingredient1.get(), ingredient1Amount),
                        FluidStackRecipeIngredient.from(ingredient2.get(), ingredient2Amount),
                        FluidStackRecipeResult.from(new FluidStack(result.get(), resultAmount)))
                .build(c, ExtremeReactors.newID("fluidizer/fluidmixing/" + name + "_1"));

        FluidizerFluidMixingRecipe.builder(FluidStackRecipeIngredient.from(ingredient2.get(), ingredient2Amount),
                        FluidStackRecipeIngredient.from(ingredient1.get(), ingredient1Amount),
                        FluidStackRecipeResult.from(new FluidStack(result.get(), resultAmount)))
                .build(c, ExtremeReactors.newID("fluidizer/fluidmixing/" + name + "_2"));
    }

    private void casing(final Consumer<IFinishedRecipe> c) {
        ShapedRecipeBuilder.shaped(Content.Items.FLUIDIZER_CASING.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('W', Items.WATER_BUCKET)
                .define('C', ContentTags.Items.INGOTS_YELLORIUM)
                .pattern("ICI")
                .pattern("CWC")
                .pattern("ICI")
                .group(GROUP_GENERAL)
                .unlockedBy("has_item", has(ContentTags.Items.INGOTS_YELLORIUM))
                .save(c, fluidizerRecipeName("casing"));
    }

    private void glass(final Consumer<IFinishedRecipe> c) {
        ShapedRecipeBuilder.shaped(Content.Items.FLUIDIZER_GLASS.get())
                .define('C', Content.Items.FLUIDIZER_CASING.get())
                .define('G', Tags.Items.GLASS)
                .pattern("GCG")
                .group(GROUP_GENERAL)
                .unlockedBy("has_item", has(Content.Items.FLUIDIZER_CASING.get()))
                .save(c, fluidizerRecipeName("glass"));
    }

    private void controller(final Consumer<IFinishedRecipe> c) {
        ShapedRecipeBuilder.shaped(Content.Items.FLUIDIZER_CONTROLLER.get())
                .define('C', Content.Items.FLUIDIZER_CASING.get())
                .define('Y', ContentTags.Items.INGOTS_YELLORIUM)
                .define('P', Tags.Items.DUSTS_GLOWSTONE)
                .define('E', Tags.Items.GEMS_EMERALD)
                .define('X', net.minecraft.item.Items.COMPARATOR)
                .pattern("CXC")
                .pattern("YEY")
                .pattern("CPC")
                .group(GROUP_GENERAL)
                .unlockedBy("has_item", has(Content.Items.FLUIDIZER_CASING.get()))
                .unlockedBy("has_item2", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(c, fluidizerRecipeName("controller"));
    }

    private void port(final Consumer<IFinishedRecipe> c, final String name, final Supplier<? extends IItemProvider> result,
                      final IItemProvider item1, final ITag<Item> tag2, final ITag<Item> tag3) {
        ShapedRecipeBuilder.shaped(result.get())
                .define('C', Content.Items.FLUIDIZER_CASING.get())
                .define('1', item1)
                .define('2', tag2)
                .define('3', tag3)
                .pattern("C2C")
                .pattern("313")
                .pattern("C2C")
                .group(GROUP_GENERAL)
                .unlockedBy("has_item", has(Content.Items.FLUIDIZER_CASING.get()))
                .unlockedBy("has_item2", has(item1))
                .save(c, fluidizerRecipeName(name));
    }

    private static ResourceLocation fluidizerRecipeName(final String name) {
        return ExtremeReactors.newID("fluidizer/" + name);
    }

    //endregion
}
