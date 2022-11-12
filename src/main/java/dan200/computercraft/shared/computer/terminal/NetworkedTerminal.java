/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.shared.computer.terminal;

import dan200.computercraft.core.terminal.Terminal;
import dan200.computercraft.core.terminal.Buffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.ArrayUtils;

public class NetworkedTerminal extends Terminal
{
    public NetworkedTerminal( int width, int height, boolean colour )
    {
        super( width, height, colour );
    }

    public NetworkedTerminal( int width, int height, boolean colour, Runnable changedCallback )
    {
        super( width, height, colour, changedCallback );
    }

    public synchronized void write( FriendlyByteBuf buffer )
    {
        buffer.writeInt( cursorX );
        buffer.writeInt( cursorY );
        buffer.writeBoolean( cursorBlink );
        buffer.writeInt( cursorBackgroundColour );
        buffer.writeInt( cursorColour );

        for( int y = 0; y < height; y++ )
        {
            Buffer<Character> text = (Buffer<Character>) this.text[y];
            Buffer<Integer> textColour = (Buffer<Integer>) this.textColour[y];
            Buffer<Integer> backColour = (Buffer<Integer>) backgroundColour[y];

            for( int x = 0; x < width; x++ ) buffer.writeByte( text.get( x ) & 0xFF );
            for( int x = 0; x < width; x++ )
            {
                buffer.writeInt( backColour.get( x ) );
                buffer.writeInt( textColour.get( x ) );
            }
        }
    }

    public synchronized void read( FriendlyByteBuf buffer )
    {
        cursorX = buffer.readInt();
        cursorY = buffer.readInt();
        cursorBlink = buffer.readBoolean();

        cursorBackgroundColour = buffer.readInt();
        cursorColour = buffer.readInt();

        for( int y = 0; y < height; y++ )
        {
            Buffer<Character> text = (Buffer<Character>) this.text[y];
            Buffer<Integer> textColour = (Buffer<Integer>) this.textColour[y];
            Buffer<Integer> backColour = (Buffer<Integer>) backgroundColour[y];

            for( int x = 0; x < width; x++ ) text.set( x, (char) (buffer.readByte() & 0xFF) );
            for( int x = 0; x < width; x++ )
            {
                backColour.set( x, buffer.readInt() );
                textColour.set( x, buffer.readInt() );
            }
        }

        setChanged();
    }

    public synchronized CompoundTag writeToNBT( CompoundTag nbt )
    {
        nbt.putInt( "term_cursorX", cursorX );
        nbt.putInt( "term_cursorY", cursorY );
        nbt.putBoolean( "term_cursorBlink", cursorBlink );
        nbt.putInt( "term_textColour", cursorColour );
        nbt.putInt( "term_bgColour", cursorBackgroundColour );

        for( int n = 0; n < height; n++ )
        {
            nbt.putString( "term_text_" + n, new String( ArrayUtils.toPrimitive( ((Buffer<Character>)text[n]).getArr() ) ) );
            nbt.putIntArray( "term_textColour_" + n, ArrayUtils.toPrimitive( ((Buffer<Integer>)textColour[n]).getArr() ) );
            nbt.putIntArray( "term_textBgColour_" + n, ArrayUtils.toPrimitive( ((Buffer<Integer>)backgroundColour[n]).getArr() ) );
        }

        return nbt;
    }

    public synchronized void readFromNBT( CompoundTag nbt )
    {
        cursorX = nbt.getInt( "term_cursorX" );
        cursorY = nbt.getInt( "term_cursorY" );
        cursorBlink = nbt.getBoolean( "term_cursorBlink" );
        cursorColour = nbt.getInt( "term_textColour" );
        cursorBackgroundColour = nbt.getInt( "term_bgColour" );

        for( int n = 0; n < height; n++ )
        {
            ((Buffer<Character>)text[n]).fill( ' ' );
            if( nbt.contains( "term_text_" + n ) )
            {
                ((Buffer<Character>)text[n]).write( ArrayUtils.toObject( nbt.getString( "term_text_" + n ).toCharArray() ) );
            }
            ((Buffer<Integer>)textColour[n]).fill( cursorColour );
            if( nbt.contains( "term_textColour_" + n ) )
            {
                ((Buffer<Integer>)textColour[n]).write( ArrayUtils.toObject( nbt.getIntArray( "term_textColour_" + n ) ) );
            }
            ((Buffer<Integer>)backgroundColour[n]).fill( cursorBackgroundColour );
            if( nbt.contains( "term_textBgColour_" + n ) )
            {
                ((Buffer<Integer>)backgroundColour[n]).write( ArrayUtils.toObject( nbt.getIntArray( "term_textBgColour_" + n ) ) );
            }
        }

        setChanged();
    }
}
