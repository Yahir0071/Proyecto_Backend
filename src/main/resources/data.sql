INSERT INTO ubicacion (id, nombre, descripcion)
VALUES (1, 'Estante Principal A', 'Pasillo de bebidas y abarrotes')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ubicacion (id, nombre, descripcion)
VALUES (2, 'Almacén de Fríos', 'Cámara de refrigeración trasera')
ON CONFLICT (id) DO NOTHING;
