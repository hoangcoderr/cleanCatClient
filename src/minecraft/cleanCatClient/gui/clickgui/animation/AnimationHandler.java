package cleanCatClient.gui.clickgui.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Advanced Animation Handler for Modern UI
 * Manages smooth animations with easing functions
 */
public class AnimationHandler {
    
    private static final Map<String, Animation> animations = new HashMap<>();
    
    /**
     * Create or update an animation
     */
    public static void animate(String id, float target, float speed) {
        Animation anim = animations.get(id);
        if (anim == null) {
            anim = new Animation(0, target, speed);
            animations.put(id, anim);
        } else {
            anim.setTarget(target);
            anim.setSpeed(speed);
        }
    }
    
    /**
     * Get current animation value
     */
    public static float getValue(String id) {
        Animation anim = animations.get(id);
        return anim == null ? 0 : anim.getValue();
    }
    
    /**
     * Set animation value immediately
     */
    public static void setValue(String id, float value) {
        Animation anim = animations.get(id);
        if (anim == null) {
            anim = new Animation(value, value, 1.0f);
            animations.put(id, anim);
        } else {
            anim.setCurrent(value);
        }
    }
    
    /**
     * Update all animations
     */
    public static void updateAll(float deltaTime) {
        for (Animation anim : animations.values()) {
            anim.update(deltaTime);
        }
    }
    
    /**
     * Reset animation
     */
    public static void reset(String id) {
        animations.remove(id);
    }
    
    /**
     * Clear all animations
     */
    public static void clearAll() {
        animations.clear();
    }
    
    /**
     * Check if animation is complete
     */
    public static boolean isComplete(String id) {
        Animation anim = animations.get(id);
        return anim != null && anim.isComplete();
    }
    
    /**
     * Animation class
     */
    public static class Animation {
        private float current;
        private float target;
        private float speed;
        private EasingType easingType;
        
        public Animation(float current, float target, float speed) {
            this(current, target, speed, EasingType.LINEAR);
        }
        
        public Animation(float current, float target, float speed, EasingType easingType) {
            this.current = current;
            this.target = target;
            this.speed = speed;
            this.easingType = easingType;
        }
        
        public void update(float deltaTime) {
            float diff = target - current;
            if (Math.abs(diff) < 0.001f) {
                current = target;
                return;
            }
            
            // Apply easing
            float step = diff * speed * deltaTime;
            float progress = Math.abs(current / target);
            float easedProgress = applyEasing(progress, easingType);
            
            current += step * (1.0f + easedProgress * 0.5f);
            
            // Clamp to target
            if ((diff > 0 && current > target) || (diff < 0 && current < target)) {
                current = target;
            }
        }
        
        public float getValue() {
            return current;
        }
        
        public void setTarget(float target) {
            this.target = target;
        }
        
        public void setSpeed(float speed) {
            this.speed = speed;
        }
        
        public void setCurrent(float current) {
            this.current = current;
        }
        
        public boolean isComplete() {
            return Math.abs(current - target) < 0.001f;
        }
        
        public void setEasingType(EasingType easingType) {
            this.easingType = easingType;
        }
    }
    
    /**
     * Easing types
     */
    public enum EasingType {
        LINEAR,
        EASE_IN,
        EASE_OUT,
        EASE_IN_OUT,
        EASE_IN_CUBIC,
        EASE_OUT_CUBIC,
        EASE_IN_OUT_CUBIC,
        EASE_OUT_ELASTIC,
        EASE_OUT_BACK
    }
    
    /**
     * Apply easing function
     */
    private static float applyEasing(float t, EasingType type) {
        switch (type) {
            case LINEAR:
                return t;
            case EASE_IN:
                return t * t;
            case EASE_OUT:
                return 1 - (1 - t) * (1 - t);
            case EASE_IN_OUT:
                return t < 0.5f ? 2 * t * t : 1 - (float)Math.pow(-2 * t + 2, 2) / 2;
            case EASE_IN_CUBIC:
                return t * t * t;
            case EASE_OUT_CUBIC:
                return 1 - (float)Math.pow(1 - t, 3);
            case EASE_IN_OUT_CUBIC:
                return t < 0.5f ? 4 * t * t * t : 1 - (float)Math.pow(-2 * t + 2, 3) / 2;
            case EASE_OUT_ELASTIC:
                float c4 = (float)(2 * Math.PI) / 3;
                return t == 0 ? 0 : t == 1 ? 1 : 
                    (float)(Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * c4) + 1);
            case EASE_OUT_BACK:
                float c1 = 1.70158f;
                float c3 = c1 + 1;
                return 1 + c3 * (float)Math.pow(t - 1, 3) + c1 * (float)Math.pow(t - 1, 2);
            default:
                return t;
        }
    }
}
