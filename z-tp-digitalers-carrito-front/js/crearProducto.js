async function crearProducto() {
    if (validarFormulario()) {
        const formData = new FormData();
        if (existeURLParam('id')) {
            formData.append('id', txtId.value.trim());
        }
        formData.append('descripcion', txtDescripcion.value.trim());
        formData.append('precio', txtPrecio.value.trim());
        formData.append('stock', txtStock.value.trim());
        formData.append('file', uplFoto.files[0]);
        const response = await fetch('http://localhost:8081/productos', {
            method: `${existeURLParam('id') ? 'POST' : 'POST'}`,
            body: formData,
        });
        switch (response.status) {
            case 201:
                alert('Producto creado.');
                break;
            case 400:
                alert('Error 404 al crear producto.');
                break;
            default:
                alert('Error 500 al crear producto.');
                break;
        }
    }
}

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

const traerInfoProducto = () => {
    if (existeURLParam('id')) {
        fetch(`http://localhost:8081/productos/${obtenerValorURLParam('id')}`)
            .then((response) => response.json())
            .then((data) => {
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

traerInfoProducto();
