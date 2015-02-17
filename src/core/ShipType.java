package core;

/**
 * Representa uma embarca��o.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public enum ShipType {
    /**
     * Representa um Porta-avi�es.
     * Tamanho: 5 casas.
     */
    PortaAvioes (5, "Porta-avi�es"),
    /**
     * Representa um Encoura�ado.
     * Tamanho: 4 casas.
     */
    Encouracado (4,  "Encoura�ado"),
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
     * Representa um Destr�ier.
     * Tamanho: 2 casas.
     */
    Destroier   (2, "Destr�ier");

    ShipType(int size, String shipName)
    {
        this.size = size;
        this.shipName = shipName;
    }
    
    private int size;
    private String shipName;

    /**
     * Tamanho da embarca��o.
     * 
     * @return Valor inteiro representando o tamanho da embarca��o.
     */
    public int size() {
        return size;
    }
    
    public String shipName() {
        return shipName;
    }
}
