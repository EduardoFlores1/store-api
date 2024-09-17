package com.api.shops.service.image;

import com.api.shops.dto.ImageDTO;
import com.api.shops.exceptions.ImageNotFoundException;
import com.api.shops.mappers.ImageMapper;
import com.api.shops.model.Image;
import com.api.shops.model.Product;
import com.api.shops.repository.ImageRepository;
import com.api.shops.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, IProductService productService, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.productService = productService;
        this.imageMapper = imageMapper;
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image Not Found With Id: " + imageId));
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId)
                .ifPresentOrElse(
                        imageRepository::delete,
                        () -> {throw new ImageNotFoundException("Image Not Found With Id: " + imageId);}
                );
    }

    @Override
    @Transactional
    public List<ImageDTO> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> imagesDTO = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/image/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);
                imagesDTO.add(imageMapper.entityToDTO(savedImage));

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imagesDTO;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
