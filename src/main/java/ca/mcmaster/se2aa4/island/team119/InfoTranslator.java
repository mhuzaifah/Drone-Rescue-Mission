package ca.mcmaster.se2aa4.island.team119;

import org.json.JSONObject;

public class InfoTranslator {

    InfoTranslator() {}

    public Information parse(JSONObject jsonInfo) {
        Information info = new Information();
        for(String key : jsonInfo.keySet()) {
            for(InfoType type : InfoType.values()) {
                if (type.matches(key)) {
                    info.store(type, jsonInfo.get(key));
                }
            }
        }
        return info;
    }

}
