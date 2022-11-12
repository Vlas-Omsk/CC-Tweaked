/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.core.terminal;

import org.apache.commons.lang3.NotImplementedException;

public class Buffer<T>
{
    private final Object[] arr;

    public Buffer( T value, int length )
    {
        arr = new Object[length];
        fill( value );
    }

    public Buffer( T[] arr )
    {
        this.arr = arr;
    }

    public int length()
    {
        return arr.length;
    }

    public T[] getArr()
    {
        // FIXME
        // return (T[]) arr;
        throw new NotImplementedException( "FIXME" );
    }

    public void write( T[] arr )
    {
        write( arr, 0 );
    }

    public void write( T[] arr, int start )
    {
        int pos = start;
        start = Math.max( start, 0 );
        int end = Math.min( start + arr.length, pos + arr.length );
        end = Math.min( end, this.arr.length );
        for( int i = start; i < end; i++ )
        {
            this.arr[i] = arr[ i - pos ];
        }
    }

    public void write( Buffer<T> buffer )
    {
        int end = Math.min( buffer.length(), this.arr.length );
        for( int i = 0; i < end; i++ )
        {
            this.arr[i] = buffer.get( i );
        }
    }

    public void fill( T value )
    {
        fill( value, 0, arr.length );
    }

    public void fill( T value, int start, int end )
    {
        start = Math.max( start, 0 );
        end = Math.min( end, arr.length );
        for( int i = start; i < end; i++ )
        {
            arr[i] = value;
        }
    }

    public T get( int i )
    {
        return (T) arr[i];
    }

    public void set( int i, T value )
    {
        if( i >= 0 && i < arr.length )
        {
            arr[i] = value;
        }
    }
}
