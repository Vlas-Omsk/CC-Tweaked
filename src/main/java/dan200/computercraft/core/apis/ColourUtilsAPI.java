/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.core.apis;

import dan200.computercraft.api.lua.ILuaAPI;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.shared.util.ColourUtils;

import java.nio.ByteBuffer;

/**
 * Colors converters.
 *
 * @cc.module colorutils
 */
public class ColourUtilsAPI implements ILuaAPI
{
    public ColourUtilsAPI( IAPIEnvironment environment )
    {
    }

    @Override
    public String[] getNames()
    {
        return new String[] { "colourutils", "colorutils" };
    }

    /**
     * Converts RGB bytes to RGB number.
     *
     * @param r Red byte.
     * @param g Green byte.
     * @param b Blue byte.
     * @return RGB number.
     */
    @LuaFunction
    public final int bytesToInt( int r, int g, int b )
    {
        return ColourUtils.bytesToInt( (byte)r, (byte)g, (byte)b );
    }

    /**
     * Converts RGB chars to RGB number.
     *
     * @param rgb RGB chars.
     * @return RGB number.
     * @throws LuaException When given string length not equals to 3.
     */
    @LuaFunction
    public final int stringToInt( ByteBuffer rgb ) throws LuaException
    {
        if ( rgb.remaining() < 3 )
        {
            throw new LuaException( "The string must have a length greater than or equal to 3." );
        }

        var pos = rgb.position();
        return ColourUtils.bytesToInt( rgb.get( pos ), rgb.get( pos + 1 ), rgb.get( pos + 2 ) );
    }

    /**
     * Converts RGB number to RGB bytes.
     *
     * @param rgb RGB number.
     * @return RGB bytes.
     * @cc.treturn number Red byte.
     * @cc.treturn number Green byte.
     * @cc.treturn number Blue byte.
     */
    @LuaFunction
    public final Object[] intToBytes( int rgb )
    {
        byte[] bytes = ColourUtils.intToBytes( rgb );
        return new Object[] { bytes[0] & 0xFF, bytes[1] & 0xFF, bytes[2] & 0xFF };
    }

    /**
     * Converts RGB number to RGB chars.
     *
     * @param rgb RGB number.
     * @return RGB chars.
     */
    @LuaFunction
    public final String intToString( int rgb )
    {
        byte[] bytes = ColourUtils.intToBytes( rgb );
        return new String( new char[] { (char)(bytes[0] & 0xFF), (char)(bytes[1] & 0xFF), (char)(bytes[2] & 0xFF) } );
    }
}
