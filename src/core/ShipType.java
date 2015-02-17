package core;

/**
 * Representa uma embarcação.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public enum ShipType {
    /**
     * Representa um Porta-aviões.
     * Tamanho: 5 casas.
     */
    PortaAvioes (5, "Porta-aviões"),
    /**
     * Representa um Encouraçado.
     * Tamanho: 4 casas.
     */
    Encouracado (4,  "Encouraçado"),
    /**
     * Representa um Cruzador.
     * Tamanho: 3 casas.
     */
    Cruzador    (3, "Cruzador"),
    /**
     * Representa um Submarino.
     * Tamanho: 3 casas.
     */
    Submarino   (3, "Submarino"),
    /**
     * Representa um Destróier.
     * Tamanho: 2 casas.
     */
    Destroier   (2, "Destróier");

    ShipType(int size, String shipName)
    {
        this.size = size;
        this.shipName = shipName;
    }
    
    private int size;
    private String shipName;

    /**
     * Tamanho da embarcação.
     * 
     * @return Valor inteiro representando o tamanho da embarcação.
     */
    public int size() {
        return size;
    }
    
    public String shipName() {
        return shipName;
    }
}
