package cleanCatClient.gui.mainmenu.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a particle effect simulating falling snowflakes.
 * This header must not be removed from this file.
 *
 * @author refactoring
 */
public class SnowPartical {
    public static  int WIDTH = Display.getWidth();
    public static  int HEIGHT = Display.getHeight();

    private final List<Particle> particles = new ArrayList<>();

    private final int particleCount;

    /**
     * Constructs a SnowfallParticles instance with the specified number of particles.
     *
     * @param particleCount The number of snowfall particles.
     */
    public SnowPartical(int particleCount) {
        this.particleCount = particleCount;
        createParticles();
    }

    /**
     * Creates a new instance of SnowfallParticles with the specified number of particles.
     *
     * @param particleCount The number of snowfall particles.
     * @return A new SnowfallParticles instance.
     */
    public static SnowPartical create(int particleCount) {
        return new SnowPartical(particleCount);
    }

    private void createParticles() {
        for (int i = 0; i < particleCount; i++) {
            float x = RandomUtils.nextFloat(0, 1920);
            float speed = 1.0f + RandomUtils.nextFloat(0, 2);
            particles.add(new Particle(x, 0, speed));
        }
    }

    private void update() {
        for (Particle particle : particles) {
            particle.update();
            if (particle.getY() > HEIGHT) {
                particle.reset();
            }
        }
    }

    /**
     * Renders the snowfall particles on the screen.
     * This method should be called once every frame.
     */
    public void render() {
        update();

        for (Particle particle : particles) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            GlStateManager.scale(1f / res.getScaleFactor(), 1f / res.getScaleFactor(), 1f / res.getScaleFactor());
            Gui.drawRect(
                    (int) particle.getX(), (int) particle.getY(),
                    (int) particle.getX() + 10, (int) particle.getY() + 10,
                    new Color(255, 255, 255, (int) (particle.getAlpha() * 255)).getRGB()
            );
            GlStateManager.popMatrix();
        }
    }

    private class Particle {

        private final float x;
        private float y;
        private final float speed;
        private float alpha;

        /**
         * Creates a new particle with the specified position and speed.
         * It updates when the update method is called.
         *
         * @param x     The initial x of the particle.
         * @param y     The initial y of the particle.
         * @param speed The speed of the particle.
         */
        public Particle(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.alpha = 1.0f;
        }

        /**
         * Updates the position.
         */
        public void update() {
            y += speed;

            float fadeStartY = HEIGHT - (HEIGHT / 3);

            if (y > fadeStartY && y < HEIGHT) {
                alpha = 1.0f - (y - fadeStartY) / (HEIGHT - fadeStartY);
            } else if (y >= HEIGHT) {
                alpha = 0.0f;
            }
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getAlpha() {
            return Math.max(alpha, 0.0f);
        }

        /**
         * Resets the position and alpha value of the particle to its initial state.
         */
        public void reset() {
            y = -20;
            alpha = 1.0f;
        }
    }
}
