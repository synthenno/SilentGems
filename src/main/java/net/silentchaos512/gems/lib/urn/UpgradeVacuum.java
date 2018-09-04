/*
 * Silent's Gems -- UpgradeVacuum
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.gems.lib.urn;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.silentchaos512.gems.block.urn.BlockSoulUrn;
import net.silentchaos512.gems.block.urn.TileSoulUrn;

public class UpgradeVacuum extends SoulUrnUpgradeBase {
    private static final int RANGE = 4;

    @Override
    public void tickTile(TileSoulUrn urn, BlockSoulUrn.LidState lid, World world, BlockPos pos) {
        if (!lid.isOpen()) return;

        Vec3d target = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE,
                pos.getX() + RANGE + 1, pos.getY() + RANGE, pos.getZ() + RANGE + 1
        );

        for (EntityItem entity : world.getEntitiesWithinAABB(EntityItem.class, axisAlignedBB)) {
            double distanceSq = entity.getDistanceSq(target.x, target.y, target.z);
            if (distanceSq < 0.5) {
                // Try to add item to urn's inventory
                urn.tryAddItemToInventory(entity.getItem());
                if (entity.getItem().isEmpty()) {
                    entity.setDead();
                }
            } else {
                // Accelerate to target point
                Vec3d vec = entity.getPositionVector().subtractReverse(target);
                vec = vec.normalize().scale(0.05);
                if (entity.posY < target.y) {
                    double xzDistanceSq = (entity.posX - target.x) * (entity.posX - target.x)
                            + (entity.posZ - target.z) * (entity.posZ - target.z);
                    vec = vec.add(0, 0.01 + xzDistanceSq / 250, 0);
                }
                entity.addVelocity(vec.x, vec.y, vec.z);
            }
        }
    }
}
