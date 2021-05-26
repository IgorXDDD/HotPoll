package com.pik.hotpoll.services;

import com.pik.hotpoll.services.interfaces.VersionService;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service("DefaultVersionService")
public class DefaultVersionService implements VersionService {
    private String version;

    @Autowired
    DefaultVersionService(){
        version = "unknown";
    }
    @Override
    public String getVersion(){
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model;
            model = reader.read(new FileReader("pom.xml"));
            version = model.getVersion();
        }catch (Exception ignored){

        }
        return version;
    }

}
