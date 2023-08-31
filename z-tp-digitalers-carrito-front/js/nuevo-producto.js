const modal = document.getElementById('modalNuevoProducto');

const crearProducto = async () => {
    if (validarFormulario()) {
        const formData = new FormData();
        if (existeURLParam('id')) {
            formData.append('id', txtId.value.trim());
        }
        formData.append('descripcion', txtDescripcion.value.trim());
        formData.append('precio', txtPrecio.value.trim());
        formData.append('stock', txtStock.value.trim());
        formData.append('file', uplFoto.files[0]);
        try {
            const response = await fetch('http://localhost:8080/productos', {
                method: `${existeURLParam('id') ? 'POST' : 'POST'}`,
                body: formData,
                headers: obtenerTokenAuthorizationHeader(new Headers()),
            });
            switch (response.status) {
                case 201:
                    mostrarModal('&#128512', 'Producto creado con éxito');
                    modal.querySelectorAll('button').forEach((boton) => {
                        boton.addEventListener('click', () => {
                            window.location.replace('./index.html');
                        });
                    });
                    break;
                case 400:
                    mostrarModal('&#128533;', 'Error 4xx al crear producto');
                    break;
                default:
                    mostrarModal('&#128533;', 'Error 5xx al crear producto');
                    break;
            }
        } catch (error) {
            mostrarModal('&#128533;', 'El servidor no está disponible (catch general)');
        }
    }
};

const validarFormulario = () => {
    const formulario = document.frmCrearProducto;
    if (formulario.txtDescripcion.value.trim() === '') {
        alert('Complete la descripción!');
        formulario.txtDescripcion.focus();
        return false;
    }
    if (formulario.txtPrecio.value.trim() === '') {
        alert('Complete el precio!');
        formulario.txtPrecio.focus();
        return false;
    }
    if (isNaN(formulario.txtPrecio.value.trim())) {
        alert('Precio debe tener un valor numérico!');
        formulario.txtPrecio.focus();
        return false;
    }
    if (formulario.txtStock.value.trim() === '') {
        alert('Complete el stock!');
        formulario.txtStock.focus();
        return false;
    }
    if (isNaN(formulario.txtStock.value.trim())) {
        alert('Stock debe tener un valor numérico!');
        formulario.txtStock.focus();
        return false;
    }
    return true;
};

const traerInfoProductoEnCasoDeEdicion = () => {
    if (existeURLParam('id')) {
        fetch(`http://localhost:8080/productos/${obtenerValorURLParam('id')}`, {
            headers: obtenerTokenAuthorizationHeader(new Headers()),
        })
            .then((response) => response.json())
            .then((data) => {
                if (!tokenValido(data)) {
                    return false;
                }
                console.log(data);
                if (data.status != 200) {
                    alert(data.mensaje);
                    return;
                }
                txtId.value = data.elemento.id;
                txtDescripcion.value = data.elemento.descripcion;
                txtPrecio.value = data.elemento.precio;
                txtStock.value = data.elemento.stock;
            });
    }
};

const mostrarModal = (carita, mensaje) => {
    const modalLogin = document.getElementById('modalNuevoProducto');
    modalLogin.querySelector('#nuevoProductoTitulo').innerHTML = carita;
    modalLogin.querySelector('.modal-body').innerHTML = mensaje + '.';
    new bootstrap.Modal(modalLogin, {}).show();
};

tokenPresente();
traerInfoProductoEnCasoDeEdicion();
