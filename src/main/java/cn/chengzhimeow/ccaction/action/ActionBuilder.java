package cn.chengzhimeow.ccaction.action;

import cn.chengzhimeow.ccaction.CCAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class ActionBuilder {
    public static Builder builder(CCAction ccAction) {
        return new Builder(ccAction);
    }

    private ActionBuilder() {
    }

    @ToString
    @EqualsAndHashCode
    public static class Builder {
        private final CCAction ccAction;
        @Getter
        private Constructor<? extends AbstractAction> Action;
        @Getter
        private Map<String, Object> params;

        private Builder(CCAction ccAction) {
            this.ccAction = ccAction;
        }

        public Builder action(Constructor<? extends AbstractAction> Action) {
            this.Action = Action;
            return this;
        }

        public Builder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        @SuppressWarnings("MethodDoesntCallSuperMethod")
        public Builder clone() {
            Builder builder = new Builder(ccAction);
            builder.Action = Action;
            builder.params = new HashMap<>(params);
            return builder;
        }

        @SneakyThrows
        public AbstractAction build() {
            return Action.newInstance(ccAction, params);
        }
    }

}
