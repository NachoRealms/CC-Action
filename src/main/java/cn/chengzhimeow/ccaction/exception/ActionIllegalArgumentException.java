package cn.chengzhimeow.ccaction.exception;

import cn.chengzhimeow.ccaction.action.AbstractAction;
import cn.chengzhimeow.ccaction.action.ArgumentKey;

import java.util.List;

public final class ActionIllegalArgumentException extends Exception {
    public final Class<? extends AbstractAction> action;
    public final List<ErrorKey> errorKeys;

    public ActionIllegalArgumentException(Class<? extends AbstractAction> action, List<ErrorKey> errorKeys) {
        this.action = action;
        this.errorKeys = errorKeys;
    }

    public enum ErrorCaused {
        NO_CAST_IMPLEMENTATION,
        CAST_ERROR,
        NOT_FOUND
    }

    public record ErrorKey(ArgumentKey key, ErrorCaused caused, Exception e) {
    }
}
