package world;

import bee.Worker;


/**
 * The field of flowers that are ripe for the worker bees to
 * gather the nectar and pollen resources.
 * The bees can arrive in any order and they are immediately allowed
 * to start gathering, as long as there is a free flower.
 * Otherwise the bee must wait until a flower becomes free.
 *
 * @author RIT CS
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 *
 */
public class FlowerField {

    /** max worker allowed in field */
    public static final int MAX_WORKERS = 10;
    /** current no of workers  */
    private int currentWorkers = 0;


    /**
     * Create the flower field.
     */
    public FlowerField() {
    }


    /**
     /**
     * When a worker bee requests entry in to the field,
     * you should first display a message:
     * *FF* {bee} enters field
     * There is only one condition that would cause a bee to have to wait -
     * if there are no flowers because all the other bees are gathering
     * from them. In this case they have to wait until a bee exits the
     * field to see if they can go next.
     * There is no control over the order the bees will follow.
     *
     * @param worker - the worker bee entering the field
     */
    public synchronized void enterField(Worker worker) throws InterruptedException {
        while(MAX_WORKERS == currentWorkers){
            wait();
        }
        System.out.println("*FF* "+ worker + " enters field");
        currentWorkers++;
    }


    /** When a worker bee is done gathering from a flower,
     * it uses this routine to indicate they are leaving, and to notify a
     * single bees that may be waiting that they should wake up and
     * check that there is indeed a free flower now.
     * At the end of this routine you should print the message:
     *FF* {bee} leaves field
     *
     * @param worker - the worker bee exiting the field
     */
    public synchronized void exitField(Worker worker){
        System.out.println("*FF* " + worker + " leaves field");
//        decrement the current no of workers in field
        currentWorkers--;
        notify();
    }
}
