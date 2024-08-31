package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class Util
{
    public static Util.EnumOS getOSType()
    {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win")) return Util.EnumOS.WINDOWS;
        if (s.contains("mac")) return Util.EnumOS.OSX;
        if (s.contains("solaris") || s.contains("sunos")) return Util.EnumOS.SOLARIS;
        if (s.contains("linux") || s.contains("unix")) return Util.EnumOS.LINUX;
        return Util.EnumOS.UNKNOWN;
    }

    public static <V> V runTask(FutureTask<V> task, Logger logger)
    {
        try
        {
            task.run();
            return task.get();
        }
        catch (ExecutionException executionexception)
        {
            handleExecutionException(executionexception, logger);
        }
        catch (InterruptedException interruptedexception)
        {
            logger.error("Error executing task", interruptedexception);
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        return null; // Or return a default value
    }

    private static void handleExecutionException(ExecutionException executionexception, Logger logger)
    {
        logger.error("Error executing task", executionexception);

        if (executionexception.getCause() instanceof OutOfMemoryError)
        {
            throw (OutOfMemoryError) executionexception.getCause();
        }
    }

    public static enum EnumOS
    {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN;
    }
}