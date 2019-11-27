package tests.interfaces.conflict;

import main.interfaces.conflict.OtherNamed;

public class OtherBase implements OtherNamed {

    @Override
    public String getName(StringBuilder builder) {
        return builder.append("OtherBase").toString();
    }
}
