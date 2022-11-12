/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.core.terminal;

// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;

class TextBufferTest
{
    // FIXME: write tests for Buffer<T>

    // @Test
    // void testStringConstructor()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     assertEquals( "test", textBuffer.toString() );
    // }

    // @Test
    // void testCharRepetitionConstructor()
    // {
    //     Buffer textBuffer = new Buffer( 'a', 5 );
    //     assertEquals( "aaaaa", textBuffer.toString() );
    // }

    // @Test
    // void testLength()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     assertEquals( 4, textBuffer.length() );
    // }

    // @Test
    // void testWrite()
    // {
    //     Buffer textBuffer = new Buffer( ' ', 4 );
    //     textBuffer.write( "test" );
    //     assertEquals( "test", textBuffer.toString() );
    // }

    // @Test
    // void testWriteTextBuffer()
    // {
    //     Buffer source = new Buffer( "test" );
    //     Buffer target = new Buffer( "    " );
    //     target.write( source );
    //     assertEquals( "test", target.toString() );
    // }

    // @Test
    // void testWriteFromPos()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.write( "il", 1 );
    //     assertEquals( "tilt", textBuffer.toString() );
    // }

    // @Test
    // void testWriteOutOfBounds()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.write( "abcdefghijklmnop", -5 );
    //     assertEquals( "fghi", textBuffer.toString() );
    // }

    // @Test
    // void testWriteOutOfBounds2()
    // {
    //     Buffer textBuffer = new Buffer( "             " );
    //     textBuffer.write( "Hello, world!", -3 );
    //     assertEquals( "lo, world!   ", textBuffer.toString() );
    // }

    // @Test
    // void testFill()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.fill( 'c' );
    //     assertEquals( "cccc", textBuffer.toString() );
    // }

    // @Test
    // void testFillSubstring()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.fill( 'c', 1, 3 );
    //     assertEquals( "tcct", textBuffer.toString() );
    // }

    // @Test
    // void testFillOutOfBounds()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.fill( 'c', -5, 5 );
    //     assertEquals( "cccc", textBuffer.toString() );
    // }

    // @Test
    // void testCharAt()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     assertEquals( 'e', textBuffer.charAt( 1 ) );
    // }

    // @Test
    // void testSetChar()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.setChar( 2, 'n' );
    //     assertEquals( "tent", textBuffer.toString() );
    // }

    // @Test
    // void testSetCharWithNegativeIndex()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.setChar( -5, 'n' );
    //     assertEquals( "test", textBuffer.toString(), "Buffer should not change after setting char with negative index." );
    // }

    // @Test
    // void testSetCharWithIndexBeyondBufferEnd()
    // {
    //     Buffer textBuffer = new Buffer( "test" );
    //     textBuffer.setChar( 10, 'n' );
    //     assertEquals( "test", textBuffer.toString(), "Buffer should not change after setting char beyond buffer end." );
    // }

    // @Test
    // void testMultipleOperations()
    // {
    //     Buffer textBuffer = new Buffer( ' ', 5 );
    //     textBuffer.setChar( 0, 'H' );
    //     textBuffer.setChar( 1, 'e' );
    //     textBuffer.setChar( 2, 'l' );
    //     textBuffer.write( "lo", 3 );
    //     assertEquals( "Hello", textBuffer.toString(), "TextBuffer failed to persist over multiple operations." );
    // }

    // @Test
    // void testEmptyBuffer()
    // {
    //     Buffer textBuffer = new Buffer( "" );
    //     // exception on writing to empty buffer would fail the test
    //     textBuffer.write( "test" );
    //     assertEquals( "", textBuffer.toString() );
    // }
}
