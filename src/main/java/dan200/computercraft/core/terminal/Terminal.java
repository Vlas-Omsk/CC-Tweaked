/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.core.terminal;

import dan200.computercraft.shared.util.ColourUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public class Terminal
{
    protected int width;
    protected int height;
    protected final boolean colour;

    protected int cursorX = 0;
    protected int cursorY = 0;
    protected boolean cursorBlink = false;
    protected int cursorColour = 0xFFFFFF;
    protected int cursorBackgroundColour = 0x000000;

    protected Object[] text;
    protected Object[] textColour;
    protected Object[] backgroundColour;

    private final @Nullable Runnable onChanged;

    public Terminal( int width, int height, boolean colour )
    {
        this( width, height, colour, null );
    }

    public Terminal( int width, int height, boolean colour, Runnable changedCallback )
    {
        this.width = width;
        this.height = height;
        this.colour = colour;
        onChanged = changedCallback;

        text = new Object[height];
        textColour = new Object[height];
        backgroundColour = new Object[height];
        for( int i = 0; i < this.height; i++ )
        {
            text[i] = new Buffer<Character>( ' ', this.width );
            textColour[i] = new Buffer<Integer>( cursorColour, this.width );
            backgroundColour[i] = new Buffer<Integer>( cursorBackgroundColour, this.width );
        }
    }

    public synchronized void reset()
    {
        cursorColour = 0xFFFFFF;
        cursorBackgroundColour = 0x0;
        cursorX = 0;
        cursorY = 0;
        cursorBlink = false;
        clear();
        setChanged();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isColour()
    {
        return colour;
    }

    public synchronized void resize( int width, int height )
    {
        if( width == this.width && height == this.height )
        {
            return;
        }

        int oldHeight = this.height;
        int oldWidth = this.width;
        Object[] oldText = text;
        Object[] oldTextColour = textColour;
        Object[] oldBackgroundColour = backgroundColour;

        this.width = width;
        this.height = height;

        text = new Object[height];
        textColour = new Object[height];
        backgroundColour = new Object[height];
        for( int i = 0; i < this.height; i++ )
        {
            if( i >= oldHeight )
            {
                text[i] = new Buffer<Character>( ' ', this.width );
                textColour[i] = new Buffer<Integer>( cursorColour, this.width );
                backgroundColour[i] = new Buffer<Integer>( cursorBackgroundColour, this.width );
            }
            else if( this.width == oldWidth )
            {
                text[i] = oldText[i];
                textColour[i] = oldTextColour[i];
                backgroundColour[i] = oldBackgroundColour[i];
            }
            else
            {
                text[i] = new Buffer<Character>( ' ', this.width );
                textColour[i] = new Buffer<Integer>( cursorColour, this.width );
                backgroundColour[i] = new Buffer<Integer>( cursorBackgroundColour, this.width );
                ((Buffer<Character>)text[i]).write( (Buffer<Character>)oldText[i] );
                ((Buffer<Integer>)textColour[i]).write( (Buffer<Integer>)oldTextColour[i] );
                ((Buffer<Integer>)backgroundColour[i]).write( (Buffer<Integer>)oldBackgroundColour[i] );
            }
        }
        setChanged();
    }

    public void setCursorPos( int x, int y )
    {
        if( cursorX != x || cursorY != y )
        {
            cursorX = x;
            cursorY = y;
            setChanged();
        }
    }

    public void setCursorBlink( boolean blink )
    {
        if( cursorBlink != blink )
        {
            cursorBlink = blink;
            setChanged();
        }
    }

    public void setTextColour( int colour )
    {
        if( cursorColour != colour )
        {
            cursorColour = colour;
            setChanged();
        }
    }

    public void setBackgroundColour( int colour )
    {
        if( cursorBackgroundColour != colour )
        {
            cursorBackgroundColour = colour;
            setChanged();
        }
    }

    public int getCursorX()
    {
        return cursorX;
    }

    public int getCursorY()
    {
        return cursorY;
    }

    public boolean getCursorBlink()
    {
        return cursorBlink;
    }

    public int getTextColour()
    {
        return cursorColour;
    }

    public int getBackgroundColour()
    {
        return cursorBackgroundColour;
    }

    private void writeToCharactersBuffer( Buffer<Character> buffer, ByteBuffer arr, int start )
    {
        int pos = start;
        int bufferPos = arr.position();

        start = Math.max( start, 0 );
        int length = arr.remaining();
        int end = Math.min( start + length, pos + length );
        end = Math.min( end, buffer.length() );
        for( int i = start; i < end; i++ )
        {
            buffer.set( i, (char) (arr.get( bufferPos + i - pos ) & 0xFF) );
        }
    }

    private void writeToIntegersBuffer( Buffer<Integer> buffer, ByteBuffer arr, int start )
    {
        int pos = start;
        int bufferPos = arr.position();

        start = Math.max( start, 0 );
        int length = arr.remaining() / 3;
        int end = Math.min( start + length, pos + length );
        end = Math.min( end, buffer.length() );
        for( int i = start; i < end; i++ )
        {
            byte r = arr.get( bufferPos + (i * 3) - pos );
            byte g = arr.get( bufferPos + (i * 3) + 1 - pos );
            byte b = arr.get( bufferPos + (i * 3) + 2 - pos );

            int rgb = ColourUtils.bytesToInt( r, g, b );

            buffer.set( i, rgb );
        }
    }

    public synchronized void blit( ByteBuffer text, ByteBuffer textColour, ByteBuffer backgroundColour )
    {
        int x = cursorX;
        int y = cursorY;
        if( y >= 0 && y < height )
        {
            writeToCharactersBuffer( (Buffer<Character>) this.text[y], text, x );
            writeToIntegersBuffer( (Buffer<Integer>) this.textColour[y], textColour, x );
            writeToIntegersBuffer( (Buffer<Integer>) this.backgroundColour[y], backgroundColour, x );
            setChanged();
        }
    }

    public synchronized void write( String text )
    {
        int x = cursorX;
        int y = cursorY;
        if( y >= 0 && y < height )
        {
            ((Buffer<Character>)this.text[y]).write( ArrayUtils.toObject( text.toCharArray() ), x );
            ((Buffer<Integer>)textColour[y]).fill( cursorColour, x, x + text.length() );
            ((Buffer<Integer>)backgroundColour[y]).fill( cursorBackgroundColour, x, x + text.length() );
            setChanged();
        }
    }

    public synchronized void scroll( int yDiff )
    {
        if( yDiff != 0 )
        {
            Object[] newText = new Object[height];
            Object[] newTextColour = new Object[height];
            Object[] newBackgroundColour = new Object[height];
            for( int y = 0; y < height; y++ )
            {
                int oldY = y + yDiff;
                if( oldY >= 0 && oldY < height )
                {
                    newText[y] = text[oldY];
                    newTextColour[y] = textColour[oldY];
                    newBackgroundColour[y] = backgroundColour[oldY];
                }
                else
                {
                    newText[y] = new Buffer<Character>( ' ', width );
                    newTextColour[y] = new Buffer<Integer>( cursorColour, width );
                    newBackgroundColour[y] = new Buffer<Integer>( cursorBackgroundColour, width );
                }
            }
            text = newText;
            textColour = newTextColour;
            backgroundColour = newBackgroundColour;
            setChanged();
        }
    }

    public synchronized void clear()
    {
        for( int y = 0; y < height; y++ )
        {
            ((Buffer<Character>)text[y]).fill( ' ' );
            ((Buffer<Integer>)textColour[y]).fill( cursorColour );
            ((Buffer<Integer>)backgroundColour[y]).fill( cursorBackgroundColour );
        }
        setChanged();
    }

    public synchronized void clearLine()
    {
        int y = cursorY;
        if( y >= 0 && y < height )
        {
            ((Buffer<Character>)text[y]).fill( ' ' );
            ((Buffer<Integer>)textColour[y]).fill( cursorColour );
            ((Buffer<Integer>)backgroundColour[y]).fill( cursorBackgroundColour );
            setChanged();
        }
    }

    public synchronized Buffer<Character> getLine( int y )
    {
        if( y >= 0 && y < height )
        {
            return (Buffer<Character>) text[y];
        }
        return null;
    }

    public synchronized void setLine( int y, String text, Integer[] textColour, Integer[] backgroundColour )
    {
        ((Buffer<Character>)this.text[y]).write( ArrayUtils.toObject( text.toCharArray() ) );
        ((Buffer<Integer>)this.textColour[y]).write( textColour );
        ((Buffer<Integer>)this.backgroundColour[y]).write( backgroundColour );
        setChanged();
    }

    public synchronized Buffer<Integer> getTextColourLine( int y )
    {
        if( y >= 0 && y < height )
        {
            return (Buffer<Integer>) textColour[y];
        }
        return null;
    }

    public synchronized Buffer<Integer> getBackgroundColourLine( int y )
    {
        if( y >= 0 && y < height )
        {
            return (Buffer<Integer>) backgroundColour[y];
        }
        return null;
    }

    public final void setChanged()
    {
        if( onChanged != null ) onChanged.run();
    }
}
