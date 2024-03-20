package ca.mcmaster.se2aa4.island.team119;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapTile  {
    private final Logger logger = LogManager.getLogger();
    private String abreviation;
    private Type tileType;
    enum Type {
        LAND,
        OCEAN,
        UNKNOWN
    }

    MapTile(String tile) {
        this.abreviation = tile;
        this.tileType = toTile(tile);
    }

    public Boolean sameTileType(MapTile tileToCompare) {
        logger.info("CHECKING IF SAME TILE");
        logger.info("{}", this.tileType);
        logger.info("{}", tileToCompare.tileType);
        logger.info("{}", this.tileType == tileToCompare.tileType);
        return this.tileType == tileToCompare.tileType;
    }

    public Type toTile(String tileType) {
        switch (tileType) {
            case "GROUND" -> { return Type.LAND; }
            case "OCEAN", "OUT_OF_RANGE" -> { return Type.OCEAN; }
            case "UNKNOWN" -> { return Type.UNKNOWN; }
            default -> { return Type.OCEAN; }
        }
    }

}

