import java.util.*;

import org.jcp.xml.dsig.internal.dom.Utils;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // TODO:
        // Parcourir les inodes de 0 à MAX_INODES - 1.
        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (i * Inode.INODE_SIZE);
        // Identifier le premier inode libre.
            int fileType = Utils.readInt(memory, offset + 4);
            }
        // Retourner son numéro.
            if (fileType == 0) {
                return i;
        }
        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // TODO:
        // Construire l'inode.
        Inode inode = new Inode(memoryManager, inodeNum);
        // L'initialiser comme fichier vide.
        long currentTime = System.currentTimeMillis();

        inode.writeToMemory(
                1,
                0,
                currentTime,
                currentTime,
                new int[Inode.DIRECT_POINTERS],
                0,
                (short) 0644,
                1);

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}