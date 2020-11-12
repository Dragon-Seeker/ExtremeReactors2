/*
 *
 * TurbineCreativeSteamGenerator.java
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

package it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.part;

import it.zerono.mods.extremereactors.gamecontent.Content;
import it.zerono.mods.zerocore.lib.data.IoDirection;
import it.zerono.mods.zerocore.lib.multiblock.ITickableMultiblockPart;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TurbineCreativeSteamGenerator
        extends AbstractTurbineEntity
        implements ITickableMultiblockPart {

    public TurbineCreativeSteamGenerator() {
        super(Content.TileEntityTypes.TURBINE_CREATIVE_STEAM_GENERATOR.get());
    }

    //region ITickableMultiblockPart

    /**
     * Called once every tick from the multiblock server-side tick loop.
     */
    @Override
    public void onMultiblockServerTick() {

        this.executeOnController(turbine -> {

            if (turbine.isMachineActive()) {
                turbine.getFluidHandler(IoDirection.Input)
                        .ifPresent(handler -> handler.fill(new FluidStack(Content.Fluids.STEAM_SOURCE.get(), turbine.getMaxIntakeRate()),
                                IFluidHandler.FluidAction.EXECUTE));
            }
        });
    }

    //endregion
}
