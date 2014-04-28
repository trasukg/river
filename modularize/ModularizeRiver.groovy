#!/usr/bin/env groovy

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

def jarMap = [
        "../lib/jsk-platform.jar"           : "apache-river/river-platform",
        "../lib/jsk-resources.jar"          : "apache-river/river-resources",
        "../lib/start.jar"                  : "apache-river/river-start",
        "../lib-dl/jsk-dl.jar"              : "apache-river/river-dl",
        "../lib/serviceui.jar"              : "apache-river/river-dl",
        "../lib/jsk-lib.jar"                : "apache-river/river-lib",
        "../lib-dl/mahalo-dl.jar"           : "apache-river/river-services/mahalo/mahalo-dl",
        "../lib/mahalo.jar"                 : "apache-river/river-services/mahalo/mahalo-service",
        "../lib-dl/mercury-dl.jar"          : "apache-river/river-services/mercury/mercury-dl",
        "../lib/mercury.jar"                : "apache-river/river-services/mercury/mercury-service",
        "../lib-dl/norm-dl.jar"             : "apache-river/river-services/norm/norm-dl",
        "../lib/norm.jar"                   : "apache-river/river-services/norm/norm-service",
        "../lib-dl/outrigger-dl.jar"        : "apache-river/river-services/outrigger/outrigger-dl",
        "../lib/outrigger.jar"              : "apache-river/river-services/outrigger/outrigger-service",
        "../lib/outrigger-snaplogstore.jar" : "apache-river/river-services/outrigger/outrigger-snaplogstore",
        "../lib-dl/reggie-dl.jar"           : "apache-river/river-services/reggie/reggie-dl",
        "../lib/reggie.jar"                 : "apache-river/river-services/reggie/reggie-service"]

def platform = []
def lib = []
def lib_dl = []
def dlMap = [:]
File src = new File(System.getProperty("user.dir"), "../src")

for(Map.Entry<String, String> entry : jarMap.entrySet()) {
    jar = entry.key
    target = entry.value
    println jar
    ZipFile zipFile = new ZipFile(jar)
    Enumeration zipEntries = zipFile.entries()
    while(zipEntries.hasMoreElements()) {
        ZipEntry zipEntry = (ZipEntry)zipEntries.nextElement()
        if(zipEntry.getName().endsWith("MANIFEST.MF"))
            continue
        if(jar.contains("jsk-resources")) {
            println "\t${zipEntry.getName()}"
        } else {
            if(!zipEntry.getName().contains("\$") && !zipEntry.isDirectory()) {
                if(jar.contains("jsk-platform")) {
                    platform << zipEntry.getName()
                    prepAndCopy(zipFile, zipEntry, src, target)
                } else if(jar.contains("jsk-dl") || jar.contains("serviceui")) {
                    lib_dl << zipEntry.getName()
                    if(skip(zipEntry, platform)) {
                        println "\t- ${zipEntry.getName()}"
                    } else {
                        prepAndCopy(zipFile, zipEntry, src, target)
                    }
                } else if(jar.contains("jsk-lib")) {
                    lib << zipEntry.getName()
                    if(skip(zipEntry, platform, lib_dl)) {
                        println "\t- ${zipEntry.getName()}"
                    } else {
                        prepAndCopy(zipFile, zipEntry, src, target)
                    }
                } else {
                    if(jar.contains("-dl")) {
                        String key = getKeyName(jar)
                        dlJarClassList = dlMap.get(key)
                        if(dlJarClassList==null)
                            dlJarClassList = []
                        dlJarClassList << zipEntry.name
                        dlMap.put key, dlJarClassList

                        if(skip(zipEntry, platform, lib_dl, lib)) {
                            println "\tSkip ${zipEntry.getName()}"
                        } else {
                            prepAndCopy(zipFile, zipEntry, src, target)
                        }
                    } else {
                        String key = getKeyName(jar)
                        dlJarClassList = dlMap.get(key)
                        if(skip(zipEntry, platform, lib_dl, lib, dlJarClassList as List)) {
                            println "\tSkip ${zipEntry.getName()}"
                        } else {
                            prepAndCopy(zipFile, zipEntry, src, target)
                        }
                    }
                }
            }
        }
    }
}

void prepAndCopy(zipFile, zipEntry, src, target) {
    String source
    if(zipEntry.getName().endsWith("class")) {
        source =
            String.format("%sjava",
                          zipEntry.getName().substring(0, zipEntry.getName().length()-"class".length()))
    } else if(zipEntry.getName().endsWith("PREFERRED.LIST")) {
        iStream = zipFile.getInputStream(zipEntry)
        //println(iStream.text)
        println("\t"+String.format("%s/src/main/resources/PREFERRED.LIST", target))
        iStream.close()
        return
    } else {
        source = zipEntry.getName()
    }
    copy(src, source, String.format("%s/src/main/java/%s", target, source))
}

void copy(src, source, target) {
    File file = new File(src, source)
    if(file.exists()) {
        File targetFile = new File(target)
        File parent = targetFile.getParentFile()
        parent.mkdirs()
        def writer = targetFile.newWriter()
        writer << file.text
        writer.flush()
        writer.close()
        println String.format("\tcp %-100s to %s", file.path, target)
    } else {
        println "\tNOT FOUND: ${file.path}"
    }
}

String getKeyName(name) {
    int ndx = name.startsWith("../lib-dl")?10:7
    String s = name.substring(ndx)
    ndx = s.endsWith("-dl.jar")?7:4
    return s.substring(0, s.length()-ndx)
}

boolean skip(ZipEntry entry, List... lists) {
    boolean skip = false
    for(List list : lists) {
        if(list==null)
            continue
        skip = !entry.name.endsWith("PREFERRED.LIST") && list.contains(entry.name)
        if(skip)
            break;
    }
    skip
}
