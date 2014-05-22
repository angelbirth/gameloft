/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resourceManagement;

import java.io.InputStream;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class ResourceManager {
    /*
     * variable
     */

    public static ResourceManager instance = null;
    public static Image[] m_imageArr = null;
    public static Sprite[] m_spriteArr = null;
    public static Object[] m_objectArr = null;

    /*
     * constructor
     */
    public ResourceManager() {
    }

    /*
     * get instance of singleton
     * @return - return instance of this class
     */
    public static ResourceManager getInstance() {
        System.out.println("create Resource manager");
        if (instance == null) {

            instance = new ResourceManager();
            System.out.println("new+++++++ Resource manager");
        }
        return instance;
    }
    /*
     * load all resource into memory
     * @param filePath - path of file text use to load
     */

    public void Load(String filePath) throws Exception {
        System.out.println("begin load resourcer");
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(inputStream);
        String head = "";

        int count = 0;
        do {
            head = scanner.getNext();
            if (head.equals("") || head.charAt(0) != '#') {
                continue;
            }
            count = Integer.parseInt(scanner.getNext());
            System.out.println("count: " + count);
            /*
             * load image
             */
            if (head.equals("#IMAGES")) {
                m_imageArr = new Image[count];
                for (int i = 0; i < count; i++) {
                    scanner.getNext();
                    try {
                        m_imageArr[i] = Image.createImage(scanner.getNext());
                        System.out.println("create image " + i);
                    } catch (Exception e) {
                    }
                }

            } /*
             * load sprite
             */ else if (head.equals("#SPRITES")) {
                m_spriteArr = new Sprite[count];
                String name = "", imageID = "";
                String[] sequenceString = null, frame = null;
                int[] sequenceInt = null;
                for (int i = 0; i < count; i++) {
                    //read name
                    scanner.getNext();
                    name = scanner.getNext();

                    //read id
                    scanner.getNext();
                    imageID = scanner.getNext();

                    //read frame w and h
                    scanner.getNext();
                    frame = Scanner.Split(scanner.getNext(), "|");

                    //read sequence
                    scanner.getNext();
                    sequenceString = Scanner.Split(scanner.getNext(), "|");
                    sequenceInt = new int[sequenceString.length];
//                    System.out.println("name : "+name+" imageID : "+imageID+ " frame : "+frame[0]+ " frame 1 : "+frame[1]+ " sequenceString [0] : "+sequenceString[0]);
                    for (int j = 0; j < sequenceString.length; j++) {
                        sequenceInt[j] = Integer.parseInt(sequenceString[j]);
                    }
                    try {
                        m_spriteArr[i] = new Sprite(
                                m_imageArr[Integer.parseInt(imageID)],
                                Integer.parseInt(frame[0]),
                                Integer.parseInt(frame[1]));
                        System.out.println("create image" + name);
                    } catch (Exception e) {
                        System.out.println("error create sprite " + name);
                    }

                    m_spriteArr[i].setFrameSequence(sequenceInt);
                }
            } /*
             * load sound
             */ else if (head.equals("#SOUNDS")) {
                for (int i = 0; i < count; i++) {
                }
            }

        } while (scanner.hasNext());
    }

    public Image getImages(int index) {
        return m_imageArr[index];
    }
}
