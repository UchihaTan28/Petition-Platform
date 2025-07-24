package com.example.petition.service;

import com.example.petition.model.Role;
import com.example.petition.model.User;
import com.example.petition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Set;
import com.example.petition.exception.CustomException;
import com.example.petition.exception.CustomException.ErrorType;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // A set of all valid BioIDs
    private static final Set<String> VALID_BIO_IDS = Set.of(
            "K1YL8VA2HG","7DMPYAZAP2","D05HPPQNJ4","2WYIM3QCK9","DHKFIYMAZ",
            "LZK7P0X0LQ","H5C98XCENC","6X6I6TSUFG","QTLCWUS8NB","Y4FC3F9ZGS",
            "V30EPKZQI2","O3WJFGR5WE","SEIQTS1H16","X16V7LFHR2","TLFDFY7RDG",
            "PGPVG5RF42","FPALKDEL5T","2BIB99Z54V","ABQYUQCQS2","9JSXWO4LGH",
            "QJXQOUPTH9","GOYWJVDA8A","6EBQ28A62V","30MY51J1CJ","FH6260T08H",
            "JHDCXB62SA","O0V55ENOT0","F3ATSRR5DQ","1K3JTWHA05","FINNMWJY0G",
            "CET8NUAE09","VQKBGSE3EA","E7D6YUPQ6J","BPX8O0YB5L","AT66BX2FXM",
            "1PUQV970LA","CCU1D7QXDT","TTK74SYYAN","4HTOAI9YKO","PD6XPNB80J",
            "BZW5WWDMUY","340B1EOCMG","CG1I9SABLL","49YFTUA96K","V2JX0IC633",
            "C7IFP4VWIL","RYU8VSS4N5","S22A588D75","88V3GKIVSF","8OLYIE2FRC"
    );

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            return bytesToHex2(hash); //
        } catch (Exception ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }

    private String bytesToHex2(byte[] hash) {
        StringBuilder builder = new StringBuilder();
        for (byte b : hash) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
        	throw new CustomException("This email is already registered.", ErrorType.DUPLICATE_EMAIL);
        }

        if (!VALID_BIO_IDS.contains(user.getBiometricId())) {
        	throw new CustomException("Invalid BioID. Please check and try again.", ErrorType.INVALID_BIO_ID);
        }

        if (userRepository.existsByBiometricId(user.getBiometricId())) {
        	throw new CustomException("This BioID has already been used.", ErrorType.DUPLICATE_BIO_ID);
        }

        // Set ADMIN role if registering with admin email, else PETITIONER
        if ("admin@petition.parliament.sr".equalsIgnoreCase(user.getEmail())) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.PETITIONER);
        }

        // Hash the password before saving
        user.setPassword(hashPassword(user.getPassword()));

        return userRepository.save(user);
    }

    
    
    public User loginUser(String email, String password) {
        System.out.println("Attempting login for email: " + email);

        User existing = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Invalid email or password.", ErrorType.INVALID_CREDENTIALS));

        System.out.println("User found: " + existing.getEmail());

        // Hash the input password
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed Input Password: " + hashedPassword);
        System.out.println("Stored Password: " + existing.getPassword());

        // Compare hashed password
        if (!existing.getPassword().equals(hashedPassword)) {
        	throw new CustomException("Invalid email or password.", ErrorType.INVALID_CREDENTIALS);
        }

        // Check role
        /*if (!"ADMIN".equals(existing.getRole()) && !"PETITIONER".equals(existing.getRole())) {
            throw new RuntimeException("Unauthorized role access.");
        }*/
        System.out.println("User Role: " + existing.getRole());

        if (existing.getRole() == null) {
            throw new RuntimeException("User role is missing.");
        }

        if (existing.getRole() != Role.ADMIN && existing.getRole() != Role.PETITIONER) {
            throw new RuntimeException("Unauthorized role access.");
        }


        return existing;
    }
}
