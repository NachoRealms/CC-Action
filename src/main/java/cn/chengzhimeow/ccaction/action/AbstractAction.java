package cn.chengzhimeow.ccaction.action;

import cn.chengzhimeow.ccaction.CCAction;
import cn.chengzhimeow.ccaction.exception.ActionIllegalArgumentException;
import cn.chengzhimeow.ccaction.exception.CastException;
import cn.chengzhimeow.ccaction.manager.CastManager;
import cn.chengzhimeow.ccaction.manager.PreProcessManager;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractAction {
    protected final CCAction ccAction;
    protected final Map<String, Object> prams;

    public AbstractAction(CCAction ccAction, Map<String, Object> prams) {
        this.ccAction = ccAction;

        Map<String, Object> finalPrams = new HashMap<>();
        for (Map.Entry<String, Object> entry : prams.entrySet()) {
            Object value = entry.getValue();

            List<PreProcessManager> list = ccAction.getPreProcessRegistry().getOrDefault(value.getClass(), new ArrayList<>());
            for (PreProcessManager pp : list) value = pp.handle(this, ccAction);
            finalPrams.put(entry.getKey(), value);
        }
        this.prams = finalPrams;
    }

    /**
     * 获取参数
     *
     * @param key  参数键
     * @param type 参数类型
     * @return 参数值
     */
    @SneakyThrows
    public final <T> T getPram(String key, Class<T> type) {
        Object value = this.prams.get(key);
        if (value == null) return null;

        CastManager castManager = this.ccAction.getCastRegistry().get(type);
        // noinspection unchecked
        return (T) castManager.cast(value, type);
    }

    /**
     * 檢查參數
     */
    public final void check() throws ActionIllegalArgumentException {
        List<ActionIllegalArgumentException.ErrorKey> errors = new ArrayList<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            ArgumentKey annotation = field.getAnnotation(ArgumentKey.class);
            if (annotation == null) continue;

            boolean notFound = true;
            Class<?> type = field.getType();
            CastManager castManager = this.ccAction.getCastRegistry().get(type);
            for (String key : annotation.keys()) {
                Object value = this.prams.get(key);
                if (value == null) continue;

                notFound = false;
                try {
                    if (castManager == null) {
                        errors.add(new ActionIllegalArgumentException.ErrorKey(annotation, ActionIllegalArgumentException.ErrorCaused.NO_CAST_IMPLEMENTATION));
                        continue;
                    }

                    castManager.cast(value, type);
                } catch (NumberFormatException | IndexOutOfBoundsException | ClassCastException | CastException e) {
                    errors.add(new ActionIllegalArgumentException.ErrorKey(annotation, ActionIllegalArgumentException.ErrorCaused.CAST_ERROR));
                }
                break;
            }

            if (notFound)
                errors.add(new ActionIllegalArgumentException.ErrorKey(annotation, ActionIllegalArgumentException.ErrorCaused.NOT_FOUND));
        }

        if (errors.isEmpty()) return;
        throw new ActionIllegalArgumentException(this.getClass(), errors);
    }

    /**
     * 初始化參數
     */
    @SneakyThrows
    public final void init() {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            ArgumentKey annotation = field.getAnnotation(ArgumentKey.class);
            if (annotation == null) continue;

            Object v = null;
            Class<?> type = field.getType();
            CastManager castManager = this.ccAction.getCastRegistry().get(type);
            for (String key : annotation.keys()) {
                Object value = this.prams.get(key);
                if (value == null) continue;

                v = castManager.cast(value, type);
                break;
            }

            if (v == null && annotation.required()) {
                throw new NullPointerException("Cannot find any key in " + Arrays.toString(annotation.keys()));
            }

            if (v != null) field.set(this, v);
        }
    }

    /**
     * 前置需求是否完成
     *
     * @return 前置需求是否完成
     */
    public boolean require() {
        return true;
    }

    /**
     * 执行操作
     */
    public final void action() {
        if (!this.require()) return;
        this.onAction();
    }

    abstract protected void onAction();
}
