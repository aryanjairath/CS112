package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {
        collageDimension = 4;
        tileDimension=150;
        originalPicture = new Picture(filename);
        collagePicture= new Picture(tileDimension*collageDimension, tileDimension*collageDimension);
        scale(originalPicture, collagePicture);
        
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {
        collageDimension = cd;
        tileDimension = td;
        originalPicture = new Picture(filename);
        collagePicture= new Picture(tileDimension*collageDimension, tileDimension*collageDimension);
        scale(originalPicture, collagePicture);


    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        for (int targetCol = 0; targetCol < target.width(); targetCol++) {
            for (int targetRow = 0; targetRow < target.height(); targetRow++) {
                int sourceCol = targetCol * source.width()  / target.width();
                int sourceRow = targetRow * source.height() / target.height();
                Color color = source.get(sourceCol, sourceRow);
                target.set(targetCol, targetRow, color);
            }
        }

    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {
        Picture tile = new Picture(tileDimension, tileDimension);
        scale(originalPicture, tile);
        Color[][] pixel = new Color[tileDimension][tileDimension];
        int width = 0;
        int height = 0;

        for(int i = 0; i < tile.width(); i++){
            for(int j = 0; j < tile.height(); j++){
                pixel[i][j] = tile.get(j, i);                
                }
            }
                for(int y = 0; y < collageDimension; y++){
                    for (int z = 0; z< collageDimension; z++){
                        for(int i = 0; i < tileDimension; i++){
                            for(int j = 0; j < tileDimension; j++){
                                collagePicture.set(width, height, pixel[i][j]);
                                width++;
                        }
                        height++;
                        width-=tileDimension;

                    }
                        width+=tileDimension;
                        height-=tileDimension;

                }

                    height+=tileDimension;
                    width=0;
            }
        
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        collageCol*=tileDimension;
        collageRow*=tileDimension;
        int width = collageCol;
        int height = collageRow;
        int x = 0;
        int g;
        if(component.equals("red"))
            x=1;
        else if(component.equals("green"))
            x=2;
        else
            x=3;

            
            for (int col = collageCol; col < (collageCol + tileDimension); col++){
                for (int row = collageRow; row < (collageRow + tileDimension); row++){
                Color color = collagePicture.get(col, row);
                if(x==1){
                    g = color.getRed();
                    collagePicture.set(col,row, new Color(g,0, 0));
                }else if(x==2){
                    g = color.getGreen();
                    collagePicture.set(col,row, new Color(0,g, 0));
                }else{
                    g= color.getBlue();
                    collagePicture.set(col,row, new Color(0,0, g));

                }      
                }
            
        }
        
    }
    

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        int c = collageCol * tileDimension;
        int r = collageRow * tileDimension;

        Picture f = new Picture(filename);
        Picture scalePic = new Picture(tileDimension, tileDimension);

        for (int i = 0; i < tileDimension; i++){
            for (int j = 0; j < tileDimension; j++){

                int scaleCol = i * f.width() / tileDimension;
                int scaleRow = j * f.height() / tileDimension;
                Color color = f.get(scaleCol, scaleRow);
                scalePic.set(i, j, color);
            }
        }

        for (int col = 0; col < this.tileDimension; col++){
            r = collageRow * this.tileDimension;
            
            for (int row = 0; row < this.tileDimension; row++){

                Color color = scalePic.get(col, row);
                collagePicture.set(c, r, color);
                r++;
            }

            c++; 

    }

    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {
        Picture tile = new Picture(tileDimension, tileDimension);
        Color[][] pixel = new Color[tileDimension][tileDimension];
        collageCol*=tileDimension;
        collageRow*=tileDimension;
        int width = collageCol;
        int height = collageRow;

        
        for (int i = collageCol; i < (collageCol + tileDimension); i++){
            for (int j = collageRow; (j < collageRow + tileDimension); j++){
                collagePicture.set(i,j, toGray(collagePicture.get(i,j)));
            }
            
        }
        
        

    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}