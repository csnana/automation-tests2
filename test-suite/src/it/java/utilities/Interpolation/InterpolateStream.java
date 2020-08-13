package utilities.Interpolation;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Sajeev Rajagopalan on 30/03/2016.
 *         CSV - Data Interpolation Stream (Copy & Replace)
 */

/*
This class is created to perform interpolation of cucumber files
Please do not modify any part of the code, tested in various operating system and working as expected.
 */


class InterpolateStream extends FilterInputStream {

    final byte[] search, replacement;
    LinkedList<Integer> inQueue = new LinkedList<Integer>();
    LinkedList<Integer> outQueue = new LinkedList<Integer>();

    protected InterpolateStream(InputStream in, byte[] search,
                                byte[] replacement) {
        super(in);
        this.search = search;
        this.replacement = replacement;
    }

    // TODO: Override the other read methods. double check the character type
    public static String byteReplace(ByteArrayInputStream bis, byte[] search, byte[] replacement) throws Exception {

        InputStream ris = new InterpolateStream(bis, search, replacement);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int b;
        while (-1 != (b = ris.read()))
            bos.write(b);

        return (new String(bos.toByteArray()));

    }

    private boolean isMatchFound() {
        Iterator<Integer> inIter = inQueue.iterator();
        for (int i = 0; i < search.length; i++)
            if (!inIter.hasNext() || search[i] != inIter.next())
                return false;
        return true;
    }

    private void readAhead() throws IOException {
        // Work up some look-ahead.
        while (inQueue.size() < search.length) {
            int next = super.read();
            inQueue.offer(next);
            if (next == -1)
                break;
        }
    }

    @Override
    public int read() throws IOException {

        // Next byte already determined.
        if (outQueue.isEmpty()) {

            readAhead();

            if (isMatchFound()) {
                for (int i = 0; i < search.length; i++)
                    inQueue.remove();

                for (byte b : replacement)
                    outQueue.offer((int) b);
            } else
                outQueue.add(inQueue.remove());
        }

        return outQueue.remove();
    }
}