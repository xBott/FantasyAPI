package me.bottdev.fantasyapi.Utils.Camera;

import me.bottdev.fantasyapi.FantasyAPI;
import me.bottdev.fantasyapi.Utils.Location.LocationTweaks;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class CameraHandler implements Listener {

    private final FantasyAPI plugin = FantasyAPI.getPlugin(FantasyAPI.class);

    private static final Set<UUID> players = new HashSet<>();
    private static final HashMap<UUID, Float> camera_start_yaw = new HashMap<>();
    private static final HashMap<UUID, Float> camera_start_pitch = new HashMap<>();

    private final Player p;

    public CameraHandler(Player p) {
        this.p = p;
    }

    public void setStartPosition(int timeout) {
        players.add(p.getUniqueId());

        camera_start_yaw.put(p.getUniqueId(), p.getLocation().getYaw());

        camera_start_pitch.put(p.getUniqueId(), p.getLocation().getPitch());

        new BukkitRunnable() {
            @Override
            public void run() {
                try {

                    if (!p.isValid()) {
                        this.cancel();
                        return;
                    }

                    Reset();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskLater(plugin, timeout + 1);
    }

    public void Reset() {
        players.remove(p.getUniqueId());
        camera_start_yaw.remove(p.getUniqueId());
        camera_start_pitch.remove(p.getUniqueId());
    }


    private float[] getDelta() {

        if (!players.contains(p.getUniqueId())) return new float[]{0.0f, 0.0f};

        Location loc = p.getLocation();

        float start_yaw = camera_start_yaw.getOrDefault(p.getUniqueId(), loc.getYaw());
        float start_pitch = camera_start_pitch.getOrDefault(p.getUniqueId(), loc.getPitch());


        float yaw_delta = -(start_yaw - loc.getYaw());
        if (Math.abs(yaw_delta) > 280) yaw_delta = start_yaw + loc.getYaw();

        float pitch_delta = start_pitch - loc.getPitch();

        return new float[]{yaw_delta, pitch_delta};
    }

    public float getDeltaYaw() {
        return getDelta()[0];
    }

    public float getDeltaPitch() {
        return getDelta()[1];
    }

    public CameraMoveDirection getDirection() {

        double delta_yaw = getDeltaYaw();
        double delta_pitch = getDeltaPitch();

        List<CameraMoveDirection> directions = new ArrayList<>();

        for (CameraMoveDirection direction : CameraMoveDirection.values()) {

            double direction_delta_yaw = direction.getDeltaYaw();
            double direction_delta_pitch = direction.getDeltaPitch();

            boolean yaw_pass = direction_delta_yaw == 0 || (direction_delta_yaw > 0 ? delta_yaw > direction_delta_yaw : delta_yaw < direction_delta_yaw);

            boolean pitch_pass = direction_delta_pitch == 0 || (direction_delta_pitch > 0 ? delta_pitch > direction_delta_pitch : delta_pitch < direction_delta_pitch);


            if (yaw_pass && pitch_pass) directions.add(direction);
        }

        return directions.isEmpty() ? getRandomDirection() : getHighestPriority(directions);
    }

    private CameraMoveDirection getHighestPriority(List<CameraMoveDirection> directions) {
        CameraMoveDirection previous = directions.get(0);
        for (CameraMoveDirection direction : directions) {
            if (previous.getPriority() < direction.getPriority()) previous = direction;
        }
        return previous;
    }

    public static CameraMoveDirection getRandomDirection() {
        CameraMoveDirection[] directions = CameraMoveDirection.values();
        return directions[new Random().nextInt(directions.length)];
    }

    public static Vector rotateVector(Location loc, CameraMoveDirection cameraMoveDirection) {
        Vector direction = loc.getDirection();
        LocationTweaks locationTweaks = new LocationTweaks();
        switch (cameraMoveDirection) {
            case LEFT -> direction = locationTweaks.rotateVectorAroundY(direction, -90);
            case RIGHT -> direction = locationTweaks.rotateVectorAroundY(direction, 90);
            case UP -> {
                loc.setPitch(-90);
                direction = loc.getDirection();
            }
            case DOWN -> {
                loc.setPitch(90);
                direction = loc.getDirection();
            }
            case DIAGONAL_LEFT_RIGHT_UP -> {
                direction = locationTweaks.rotateVectorAroundY(direction, 90);
                loc.setDirection(direction);
                loc.setPitch(-45);
                direction = loc.getDirection();
            }
            case DIAGONAL_LEFT_RIGHT_DOWN -> {
                direction = locationTweaks.rotateVectorAroundY(direction, 90);
                loc.setDirection(direction);
                loc.setPitch(45);
                direction = loc.getDirection();
            }
            case DIAGONAL_RIGHT_LEFT_UP -> {
                direction = locationTweaks.rotateVectorAroundY(direction, -90);
                loc.setDirection(direction);
                loc.setPitch(-45);
                direction = loc.getDirection();
            }
            case DIAGONAL_RIGHT_LEFT_DOWN -> {
                direction = locationTweaks.rotateVectorAroundY(direction, -90);
                loc.setDirection(direction);
                loc.setPitch(45);
                direction = loc.getDirection();
            }
        }
        return direction;
    }

}
