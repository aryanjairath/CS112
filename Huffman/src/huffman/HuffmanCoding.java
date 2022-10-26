package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        int[] freq = new int[128];
        int total = 0;
        char x;
        sortedCharFreqList = new ArrayList<CharFreq>();
        while(StdIn.hasNextChar()){
            x=StdIn.readChar();
            freq[x]+=1;
            total++;
        }
        for(int i = 0; i < freq.length; i++){
            if(freq[i]>0){
            CharFreq obj = new CharFreq((char)(i), (double)freq[i]/total);
            sortedCharFreqList.add(obj);
            
            }
        }
        if(sortedCharFreqList.size()==1){
           CharFreq one = sortedCharFreqList.get(0);
           int y = one.getCharacter();
           CharFreq two;
            if(y==127)
                two = new CharFreq((char)(0),0.0);
            else
                two = new CharFreq((char)(y+1), 0.0);
            sortedCharFreqList.add(two);

        }
        Collections.sort(sortedCharFreqList);    
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    
    public void makeTree() {
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();
        Queue<TreeNode> dequeued = new Queue<TreeNode>();
        for(int i = 0; i < sortedCharFreqList.size(); i++){
            TreeNode n = new TreeNode(sortedCharFreqList.get(i), null, null);
            source.enqueue(n);
        }
        while(!source.isEmpty()||target.size()!=1){
            while(dequeued.size()<2){
                if(target.isEmpty()){
                    TreeNode x = source.dequeue();
                    dequeued.enqueue(x);
                }else{
                    if(!source.isEmpty()){
                        if(source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc())
                            dequeued.enqueue(source.dequeue());
                        else
                            dequeued.enqueue(target.dequeue());
                    }else{
                        dequeued.enqueue(target.dequeue());
                    }
                }

        }
            
            TreeNode smallestNode;
            TreeNode secondSmallestNode;

            if(dequeued.isEmpty())
                smallestNode = null;
            else
                smallestNode = dequeued.dequeue();
            if(dequeued.isEmpty())
                secondSmallestNode = null;
            else
                secondSmallestNode = dequeued.dequeue();

            double probOcc1=0;
            double probOcc2=0;

            if(smallestNode!=null)
                probOcc1 = smallestNode.getData().getProbOcc();
            if(secondSmallestNode!=null)
                probOcc2 = secondSmallestNode.getData().getProbOcc();
            TreeNode temp = new TreeNode();
            if(probOcc1>probOcc2){
                temp = smallestNode;
                smallestNode=secondSmallestNode;
                secondSmallestNode=temp;

            }



            CharFreq f = new CharFreq(null, probOcc1+probOcc2);
            f.getProbOcc();
            TreeNode combine = new TreeNode(f, smallestNode, secondSmallestNode);
            target.enqueue(combine);

    }
        /*boolean bol = true;
        int counter = 0;
        for(int i = 0; i < sortedCharFreqList.size(); i++){
            TreeNode n = new TreeNode(sortedCharFreqList.get(i), null, null);
            source.enqueue(n);
        }
        TreeNode x, y, z;
        CharFreq f;
        if(target.isEmpty()){
            x = source.dequeue();
            y = source.dequeue();
            f = new CharFreq(null, x.getData().getProbOcc()+y.getData().getProbOcc());
            z = new TreeNode(f, x, y);
            target.enqueue(z);
        }
        while(!source.isEmpty()  || target.size()!=1){
            bol = false;
            counter++;
            System.out.println(source.size());;
            x = new TreeNode();
            y = new TreeNode();
            z = new TreeNode();
            if(!source.isEmpty()&& source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc()){
                x = source.dequeue();
                if(!source.isEmpty()&& source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc())
                    y = source.dequeue();
                else
                    y = target.dequeue();
                f = new CharFreq(null, x.getData().getProbOcc()+y.getData().getProbOcc());
                if(x.getData().getProbOcc()>y.getData().getProbOcc())
                    z = new TreeNode(f, y, x);
                else
                    z = new TreeNode(f,x,y);
                target.enqueue(z);
                counter++;
            }
            if(!source.isEmpty()&& source.peek().getData().getProbOcc()>target.peek().getData().getProbOcc()){
                x = target.dequeue();
                if(!source.isEmpty()&& source.peek().getData().getProbOcc()>target.peek().getData().getProbOcc())
                    y = target.dequeue();
                else
                    y = source.dequeue();
                f = new CharFreq(null, x.getData().getProbOcc()+y.getData().getProbOcc());
                if(x.getData().getProbOcc()>y.getData().getProbOcc())
                    z = new TreeNode(f, y, x);
                else
                    z = new TreeNode(f,x,y);
                target.enqueue(z);                target.enqueue(z); 
        }
        if(source.size()==1&&target.size()==1){
            if(!source.isEmpty()&& source.peek().getData().getProbOcc()<=target.peek().getData().getProbOcc()){
                x=source.dequeue();
                y=target.peek();
            }
            else{
                y = source.peek();
                x = target.dequeue();
            }
            f = new CharFreq(null, x.getData().getProbOcc()+y.getData().getProbOcc());
            if(x.getData().getProbOcc()>y.getData().getProbOcc())
            z = new TreeNode(f, y, x);
        else
            z = new TreeNode(f,x,y);
        target.enqueue(z);
                    if(!target.isEmpty())
            target.dequeue();
            target.enqueue(z);
        }
    }*/
            huffmanRoot = target.dequeue();
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */

     //Initializing the encoding array to send to inOrder
    public void makeEncodings() {
        encodings = new String[128];
        TreeNode copyOfRoot = huffmanRoot;
        String empty = "";
        for(int i = 0; i < sortedCharFreqList.size(); i++){
            inOrder(copyOfRoot, sortedCharFreqList.get(i).getCharacter(), empty);
        }
    }
    private void inOrder(TreeNode rootCopy, char get, String add){
        if(rootCopy == null)
            return;
        add+="0";
        inOrder(rootCopy.getLeft(), get, add);
        if(rootCopy.getData().getCharacter()!=null && rootCopy.getData().getCharacter()==get ){
                encodings[get] = add;
            return;

        }
        add+="1";
        inOrder(rootCopy.getRight(), get, add);
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String write = "";
        while(StdIn.hasNextChar()){
            write+=encodings[StdIn.readChar()];
        }
        writeBitString(encodedFile, write);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String written = readBitString(encodedFile);
        TreeNode copy = huffmanRoot;
        for(int i = 0; i < written.length(); i++){
            
            if(written.charAt(i)=='0'&& copy.getLeft()!=null)
                copy = copy.getLeft();
            if(written.charAt(i)=='1'&&copy.getRight()!=null)
                copy = copy.getRight();
            if(copy.getLeft()==null && copy.getRight()==null){
                StdOut.print(copy.getData().getCharacter());
                copy=huffmanRoot;
            }
            
        }
        
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
