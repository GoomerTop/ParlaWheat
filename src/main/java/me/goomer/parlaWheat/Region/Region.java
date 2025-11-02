package me.goomer.parlaWheat.Region;

import me.goomer.parlaWheat.ParlaWheat;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Region {
    private Location pos1, pos2, star;
    private String world;
    private int count;
    private int delay;
    private String material;
    private String key;

    public Region(Location pos1, Location pos2, String world, int count, int delay, String material, String key){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        this.count = count;
        this.material = material;
        this.delay = delay;
        this.key = key;
        this.star=null;
    }

    public Region(Location pos1, Location pos2, String world, int count, int delay, String material, String key, Location star){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        this.count = count;
        this.material = material;
        this.delay = delay;
        this.key = key;
        this.star = star;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public String getWorld() {
        return world;
    }

    public int getCount() {
        return count;
    }

    public int getDelay() {
        return delay;
    }

    public String getMaterial() {
        return material;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Location getStar() {
        return star;
    }

    public void setStar(Location star) {
        this.star = star;
    }

    public boolean contains(Location loc){
        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return minX <= loc.getX() && loc.getX() <= maxX &&
                minZ <= loc.getZ() && loc.getZ() <= maxZ
                && minY <= loc.getY() && loc.getY() <= maxY;
    }

    public void drawParticles(ParlaWheat plugin, Location end){
        end.add(0.5, 0.5, 0.5);
        Location star = this.star.clone().add(0.5, 0.5, 0.5);
        int pointsPerLine = 20;
        Vector vector = end.clone().subtract(star).toVector().multiply(1.0 / pointsPerLine);

        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                if (ticks > getDelay()) {
                    cancel();
                    return;
                }
                for (int i = 0; i <= pointsPerLine; i++) {
                    Location point = star.clone().add(vector.clone().multiply(i));
                    Particle.DustOptions dust = new Particle.DustOptions(Color.WHITE, 1);
                    star.getWorld().spawnParticle(Particle.DUST, point, 1, 0, 0, 0, dust);
                }
                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 1); // כל טיק
    }


}
