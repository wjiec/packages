package main.interfaces.conflict;

import java.util.Objects;

public interface DefaultNamed {

    default String getName() {
        return this.getClass().getName() + Objects.hashCode(this);
    }

}
