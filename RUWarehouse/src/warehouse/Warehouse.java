package warehouse;
public class Warehouse {
    private Sector[] sectors;

    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }

    /**
     * Provided method, code the parts to add their behavior
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        int sector = id % 10;
        Product prod = new Product(id, name, stock, day, demand);
        sectors[sector].add(prod);
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * 
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        sectors[id % 10].swim(sectors[id % 10].getSize());

    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5
     * while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the
     * Sector class
     * 
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int sector = id % 10;
        if (sectors[sector].getSize() == 5) {
            sectors[sector].swap(1, 5);
            sectors[sector].deleteLast();
            sectors[sector].sink(1);
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * 
     * @param id     The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int sector = id % 10;
        for (int i = 1; i <= sectors[sector].getSize(); i++) {
            if (sectors[sector].get(i).getId() == id) {
                sectors[sector].get(i).updateStock(amount);
                return;
            }
        }
    }

    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(),
     * .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * 
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int sector = id % 10;
        for (int i = 1; i <= sectors[sector].getSize(); i++) {
            if (sectors[sector].get(i).getId() == id) {
                sectors[sector].swap(i, sectors[sector].getSize());
                sectors[sector].deleteLast();
                sectors[sector].sink(i);
                return;
            }
        }
    }

    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector
     * class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(),
     * updateStock(), updateDemand() methods
     * 
     * @param id     The id of the purchased product
     * @param day    The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int sector = id % 10;
        for (int i = 1; i <= sectors[sector].getSize(); i++) {
            if (sectors[sector].get(i).getId() == id) {
                if (amount > sectors[sector].get(i).getStock())
                    return;
                sectors[sector].get(i).updateDemand(amount);
                sectors[sector].get(i).updateStock(amount * -1);
                sectors[sector].get(i).setLastPurchaseDay(day);
                sectors[sector].sink(i);

            }
        }
    }

    /**
     * Construct a better scheme to add a product, where empty spaces are always
     * filled
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        /*
         * int sec = id%10;
         * int counter = 0;
         * 
         * if(sectors[sec].getSize()<5){
         * sectors[sec].add(new Product(id, name, stock, day, demand));
         * }else{
         * sec++;
         * while(counter <= 9){
         * if(sec<sectors.length){
         * if(sectors[sec].getSize()<5){
         * sectors[sec].add(new Product(id, name, stock, day, demand));
         * sectors[sec].swim(sectors[sec].getSize());
         * return;
         * }else
         * sec++;
         * }else{
         * sec=0;
         * }
         * counter++;
         * }
         * }
         */
        for(int i = id; i<id+10; i++){
            if(sectors[i%10].getSize()<5){
                sectors[i%10].add(new Product(id, name, stock, day, demand));
                fixHeap(i);
                return;
            }
        }

        addProduct(id, name, stock, day, demand);
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }

        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */
    public Sector[] getSectors() {
        return sectors;
    }
}