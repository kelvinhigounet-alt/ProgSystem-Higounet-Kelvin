import org.jcp.xml.dsig.internal.dom.Utils;

public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        // TODO:
        // Calculer l'offset exact de l'inode.
        return MemoryManager.INODE_TABLE_OFFSET + (inodeNumber * INODE_SIZE);
    }

    public int getFileType() {
        // TODO:
        // Lire le type à offset + 4.
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset();
        return Utils.readInt(memory, offset + 4);
    }

    public int getFileSize() {
        // TODO:
        // Lire la taille à offset + 8.
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset();
        return Utils.readInt(memory, offset + 8);
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

        // TODO:
        // Lire les 10 pointeurs directs.
        int offset = getInodeOffset();
        for (int i = 0; i < DIRECT_POINTERS; i++) {
            pointers[i] = Utils.readInt(memory, offset + 12 + (i * 4));
        }

        return pointers;
    }

    public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int offset = getInodeOffset();

        // TODO:
        // 1. Numéro d'inode
        Utils.writeInt(memory, offset, inodeNumber);
        // 2. Type
        Utils.writeInt(memory, offset + 4, fileType);
        // 3. Taille
        Utils.writeInt(memory, offset + 8, fileSize);
        // 4. Création
        Utils.writeLong(memory, offset + 12, creationTime);
        // 5. Modification
        Utils.writeLong(memory, offset + 20, modificationTime);
        // 6. 10 pointeurs directs
        for (int i = 0; i < DIRECT_POINTERS; i++) {
            Utils.writeInt(memory, offset + 28 + (i * 4), directPointers[i]);
        }
        // 7. Pointeur indirect
        Utils.writeInt(memory, offset + 68, indirectPointer);
        // 8. Permissions
        Utils.writeShort(memory, offset + 72, permissions);
        // 9. Nombre de liens
        Utils.writeInt(memory, offset + 74, linkCount);
    }
}