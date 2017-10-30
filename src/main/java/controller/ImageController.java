package controller;

import dataClass.Subscription;
import dataClass.User;
import model.UserTableInteract;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

@RestController
@RequestMapping("/image")
public class ImageController {
    @RequestMapping(value="/{imageID}",method= RequestMethod.GET)
    public ResponseEntity<byte[]> get(@PathVariable("imageID") String iid) {
        byte[] b = new byte[1];
        try {
            RandomAccessFile f = new RandomAccessFile("images/" + iid  , "r");
            b = new byte[(int)f.length()];
            f.readFully(b);

        }
        catch (IOException e) {
            System.out.println("invalid img path");
        }
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
    }
}
