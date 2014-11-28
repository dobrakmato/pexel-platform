// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class for BungeePatcher. Patcher, that replaces default BungeeSecurityManager in BungeeCord.jar with custom one
 * to allow creation of threads and other things. Uses javac and jar.
 */
public class BungeePatcher {
    private static final String PATCH_1_CONTENT  = "package net.md_5.bungee;\nimport java.net.InetAddress;import java.io.FileDescriptor;import java.security.Permission;public class BungeeSecurityManager extends SecurityManager { public static final int PATCHED = 12;@Override public void checkPermission(final Permission perm) { } @Override public void checkPermission(final Permission perm, final Object context) { } @Override public void checkPropertyAccess(final String key) { } @Override public void checkCreateClassLoader() { } @Override public void checkAccess(final Thread t) { } @Override public void checkAccess(final ThreadGroup g) { } @Override public void checkExit(final int status) { } @Override public void checkExec(final String cmd) { } @Override public void checkLink(final String lib) { } @Override public void checkRead(final FileDescriptor fd) { } @Override public void checkRead(final String file) { } @Override public void checkRead(final String file, final Object context) { } @Override public void checkWrite(final FileDescriptor fd) { } @Override public void checkWrite(final String file) { } @Override public void checkDelete(final String file) { } @Override public void checkConnect(final String host, final int port) { } @Override public void checkConnect(final String host, final int port, final Object context) { } @Override public void checkListen(final int port) { } @Override public void checkAccept(final String host, final int port) { } @Override public void checkMulticast(final InetAddress maddr) { } @Override public void checkMulticast(final InetAddress maddr, final byte ttl) { } @Override public void checkPropertiesAccess() { } @Override public void checkPrintJobAccess() { } @Override public void checkSystemClipboardAccess() { } @Override public void checkAwtEventQueueAccess() { } @Override public void checkPackageAccess(final String pkg) { } @Override public void checkPackageDefinition(final String pkg) { } @Override public void checkSetFactory() { } @Override public void checkMemberAccess(final Class clazz, final int which) { } @Override public void checkSecurityAccess(final String target) { } }";
    private static final String PATCH_1_PATH     = "net/md_5/bungee/";
    private static final String PATCH_1_FILENAME = "BungeeSecurityManager.java";
    
    public static void main(final String[] args) {
        System.out.println("BungeeCordPatcher v. 1.0");
        System.out.println("Finding BungeeCord.jar...");
        for (File f : new File(".").listFiles()) {
            if (f.getName().contains("BungeeCord") && f.getName().endsWith(".jar")) {
                try {
                    patch(f);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        System.out.println("Thanks for using!");
    }
    
    private static void patch(final File f) throws IOException, InterruptedException {
        System.out.println("Patching file " + f.getName() + "...");
        generatePatch(f);
        applyPatch(f);
        removePatch(f);
        System.out.println("File " + f.getName() + " has been patched!");
    }
    
    private static void generatePatch(final File f) throws IOException,
            InterruptedException {
        System.out.println("Generating sources...");
        File f1 = new File(f.getParentFile().getAbsolutePath() + "/"
                + BungeePatcher.PATCH_1_PATH);
        f1.mkdirs();
        FileWriter fw = new FileWriter(new File(f1.getAbsolutePath() + "/"
                + BungeePatcher.PATCH_1_FILENAME));
        fw.append(BungeePatcher.PATCH_1_CONTENT);
        fw.close();
        System.out.println("Building sources, please wait...");
        Runtime.getRuntime()
                .exec("javac " + f1.getAbsolutePath() + "/"
                        + BungeePatcher.PATCH_1_FILENAME)
                .waitFor();
        System.out.println("Building finished!");
    }
    
    private static void applyPatch(final File f) throws InterruptedException,
            IOException {
        System.out.println("Starting patching process, please wait...");
        Runtime.getRuntime()
                .exec("jar uf " + f.getName() + " " + BungeePatcher.PATCH_1_PATH
                        + BungeePatcher.PATCH_1_FILENAME.replace(".java", ".class"))
                .waitFor();
        System.out.println("Patching finished!");
    }
    
    private static void removePatch(final File f) {
        System.out.println("Cleaning up...");
    }
    
}
