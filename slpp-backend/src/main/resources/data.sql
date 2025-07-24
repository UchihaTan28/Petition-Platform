-- Insert a default admin user (hashed password is just an example)
INSERT INTO app_user (email, full_name, date_of_birth, password, biometric_id, role) 
VALUES (
    'admin@petition.parliament.sr',
    'Admin User',
    '1980-01-01',
    '28c2bbef858481a63dd136bfc4d152db63e6b24295dbeb566813fee4197f8ce1',
    --'4f29b54eb0e30f2b43e8a6d50e2f3e1dd2df4f2cbe2a38e88f90c7c9efc89aa6',  -- Hashed '2025%shangrila'
    'ADMINBIO123',
    'ADMIN'
) ON CONFLICT (email) DO NOTHING;  -- Avoid duplicates


-- Insert sample petitioner user
INSERT INTO app_user (email, full_name, date_of_birth, password, biometric_id, role)
VALUES (
    'john@example.com',
    'John Doe',
    '1995-05-15',
    '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd38ab5f8dbf83c2f98',  -- SHA-256("password")
    'K1YL8VA2HG',
    'PETITIONER'
);

-- Sample petition
INSERT INTO petition (title, text, status, response, signatures, creator_id)
VALUES (
    'Protect Forests',
    'We need to preserve Shangri-La forests. Please sign.',
    'open',
    NULL,
    0,
    (SELECT id FROM app_user WHERE email='john@example.com')
);
