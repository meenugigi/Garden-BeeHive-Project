package world;

import bee.Drone;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The queen's chamber is where the mating ritual between the queen and her
 * drones is conducted. The drones will enter the chamber in order.
 * If the queen is ready and a drone is in here, the first drone will be
 * summoned and mate with the queen. Otherwise the drone has to wait.
 * After a drone mates they perish, which is why there is no routine for
 * exiting (like with the worker bees and the flower field).
 *
 * @author RIT CS
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 *
 */
public class QueensChamber {

    /** queue of drones */
    private ConcurrentLinkedQueue<Drone> droneQueue;
    /** flag to check if mating occured */
    private boolean mated = false;
    /** flag to check if queen is ready to mate */
    public boolean isReady = false;
    /** the drone */
    private Drone drone1;

    public Drone getDrone1() {
        return drone1;
    }

    /**Create the chamber.
     * Initially there are no drones in the chamber and the queen is not ready to mate.
     *
     */
    public QueensChamber(){
        droneQueue = new ConcurrentLinkedQueue<>();
    }


    /** A drone enters the chamber. The first thing you should display is:
     *QC* {bee} enters chamber
     * The bees should be stored in some queue like collection.
     * If the queen is ready and this drone is at the front of the collection,
     * they are allowed to mate. Otherwise they must wait.
     * The queen isn't into any of this kinky multiple partner stuff
     * while she is mating with a drone, she is not ready to mate again.
     * When the drone leaves this method, display the message:
     *QC* {bee} leaves chamber
     *
     * @param drone - the drone who just entered the chamber
     */
    public synchronized void enterChamber(Drone drone) throws InterruptedException {
            System.out.println("*QC* " + drone +" enters chamber");
            droneQueue.add(drone);

            if (isReady == false){
                wait();
            }
            if (mated && drone1.equals(droneQueue.peek())){
                System.out.println("*QC* " + drone1 +" leaves chamber");
            }
    }


    /** When the queen is ready,
     * they will summon the next drone from the collection
     * (if at least one is there).
     * The queen will mate with the first drone and display a message:
     *QC* Queen mates with {bee}
     * It is the job of the queen if mating to notify all of the
     * waiting drones so that the first one can be selected since
     * we can't control which drone will unblock.
     * Doing a notify will lead to deadlock if the drone that unblocks is not
     * the front one.
     *
     */
    public synchronized void summonDrone(){
            drone1 = droneQueue.peek();
            System.out.println("*QC* Queen mates with " + droneQueue.poll());
            mated = true;
            notifyAll();
    }


    /** At the end of the simulation the queen uses this routine
     * repeatedly to dismiss all the drones that were waiting to mate.
     *
     */
    public synchronized void dismissDrone(){
            if(hasDrone()){
                for(Drone droneWaitingToMate : droneQueue){
                    droneQueue.remove(droneWaitingToMate);
                    notify();
                }
        }

    }

    /** To check if there are any more drones.
     *
     */
    public boolean hasDrone(){
        if(droneQueue.size() > 0){
            return true;
        }
        return false;
    }


    /** to check if mating has occurred or not.
     *
     * @return
     */
    public boolean getMatedStatus(){
        return mated;
    }
}