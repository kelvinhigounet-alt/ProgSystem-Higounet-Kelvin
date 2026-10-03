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

    public boolean writeFile(
            int inodeNum,
            byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers =
                new int[Inode.DIRECT_POINTERS];

        // TODO:
        // Allouer blocksNeeded blocs.
        for (int i = 0; i < blocksNeeded; i++) {
            int blockNumber =
                    memoryManager.allocateBlock();

            if (blockNumber == -1) {

                // Libérer les blocs déjà alloués.
                for (int j = 0; j < i; j++) {
                    memoryManager.setBlockUsed(
                            blockPointers[j], false);
                }

                return false;
            }

            blockPointers[i] = blockNumber;
        }

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        // TODO:
        // Pour chaque bloc :
        // - calculer la quantité à copier ;
        // - récupérer le numéro du bloc ;
        // - calculer son offset physique ;
        // - copier les données.
        for (int i = 0; i < blocksNeeded; i++) {
            int bytesToCopy =
                    Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);

            int blockOffset = blockPointers[i] * MemoryManager.BLOCK_SIZE;

            System.arraycopy(data, dataSrcOffset, memory, blockOffset, bytesToCopy);

            dataSrcOffset += bytesToCopy;
            bytesRemaining -= bytesToCopy;
        }


        // TODO:
        // Mettre à jour l'inode.
        Inode inode = new Inode(memoryManager, inodeNum);
        long currentTime = System.currentTimeMillis();
        inode.writeToMemory(
                1,
                data.length,
                currentTime,
                currentTime,
                blockPointers,
                0,
                (short) 0644,
                1);

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();

        // TODO:
        // Parcourir les blocs utilisés.
        // Copier chaque fragment vers fileData.
        int bytesRemaining = fileSize;
        int dataDestOffset = 0;
        int blocksNeeded = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;

        for (int i = 0; i < blocksNeeded; i++) {
            int bytesToCopy =
                    Math.min(
                            bytesRemaining,
                            MemoryManager.BLOCK_SIZE);

            int blockOffset =
                    blockPointers[i]
                    * MemoryManager.BLOCK_SIZE;

            System.arraycopy(
                    memory,
                    blockOffset,
                    fileData,
                    dataDestOffset,
                    bytesToCopy);

            dataDestOffset += bytesToCopy;
            bytesRemaining -= bytesToCopy;
        }
        return fileData;
    }
}