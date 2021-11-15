const verCarrito = () => {
    vaciarTabla();
    const productosCarritoTemp = obtenerCarritoSS();
    const tableHead = document.querySelector('#lista-carrito thead');
    const tableBody = document.querySelector('#lista-carrito tbody');
    const tableFooter = document.querySelector('#lista-carrito tfoot');
    if (productosCarritoTemp.length > 0) {
        document.getElementById('msj').textContent = '';
        tableHead.innerHTML = `<tr>
                        <th>Foto</th>
                        <th>Descripción</th>
                        <th>Precio unitario</th>
                        <th>Subtotal</th>
                        <th>Cantidad</th>
                        <th>Acciones</th>
                    </tr>`;
        productosCarritoTemp.forEach((element) => {
            tableBody.innerHTML += `<tr class="align-middle" data-id="${element.id}">
                        <td><img src="${element.URLImagen}" alt="${element.descripcion}" class="img-fluid" style="width: 200px;"></td>
                        <td>${element.descripcion}</td>
                         <td>${formatearMoneda(element.precio)}</td>
                         <td class="subtotal">${formatearMoneda(element.precio * element.cantidad)}</td>
                           <td class="cantidad">${element.cantidad}</td>
                            <td>
                            <button type="button" class="btn btn-outline-primary btnSumar" onclick="sumarUno(this)">+</button>
                           <button type="button" class="btn btn-outline-secondary btnRestar" onclick="restarUno(this)">-</button>
                            <button type="button" class="btn btn-outline-danger btnEliminar" onclick="eliminar(this)">Eliminar</button></td>
                        </tr>`;
        });
        tableFooter.innerHTML = `
                                <tr class="table-dark h6">
                                    <td colspan="3"></td>
                                    <td>Total</td>
                                    <td class="total">${formatearMoneda(calcularTotalCarritoSS(productosCarritoTemp))}</td>
                                    <td>
                                    <button type="button" class="btn btn-danger" onclick="vaciarCarrito()">Vaciar carrito</button>
                                    <button type="button" class="btn btn-primary" onclick="efectuarPago()">Pagar</button>
                                    </td>
                                </tr>
        `;
    } else {
        vaciarTabla();
    }
};

const actualizarCantidadProducto = (idProducto, incremento) => {
    // En session storage.
    actualizarCantidadCarritoSS(idProducto, incremento);

    // En la tabla.
    const producto = obtenerProductoSS(idProducto);
    const filaTabla = document.querySelector(`#lista-carrito > tbody [data-id = '${idProducto}']`);
    filaTabla.querySelector('.cantidad').textContent = producto.cantidad;

    filaTabla.querySelector('.subtotal').textContent = formatearMoneda(producto.cantidad * producto.precio);
    const tableFooter = document.querySelector('#lista-carrito tfoot');
    tableFooter.querySelector('.total').textContent = formatearMoneda(calcularTotalCarritoSS());
};

const vaciarTabla = () => {
    const tableHead = document.querySelector('#lista-carrito thead');
    const tableBody = document.querySelector('#lista-carrito tbody');
    const tableFooter = document.querySelector('#lista-carrito tfoot');
    tableHead.innerHTML = '';
    tableBody.innerHTML = '';
    tableFooter.innerHTML = '';
    document.getElementById('msj').textContent = 'El carrito no tiene ningún elemento cargado.';
};

const sumarUno = (boton) => {
    const idProducto = boton.parentElement.parentElement.dataset.id;
    actualizarCantidadProducto(idProducto, 1);
};

const restarUno = (boton) => {
    const idProducto = boton.parentElement.parentElement.dataset.id;
    actualizarCantidadProducto(idProducto, -1);
};

const eliminar = (boton) => {
    // En session storage.
    const idProducto = boton.parentElement.parentElement.dataset.id;
    quitarDelCarritoSS(idProducto);

    // En la tabla.
    const idFila = boton.parentNode.parentNode.rowIndex;
    document.querySelector('#lista-carrito').deleteRow(idFila);
    if (carritoEstaVacioSS()) {
        vaciarTabla();
    } else {
        document.querySelector('#lista-carrito tfoot .total').textContent = formatearMoneda(calcularTotalCarritoSS());
    }
};

const vaciarCarrito = () => {
    sessionStorage.removeItem('productosCarritoTemp');
    vaciarTabla();
};

const efectuarPago = async () => {
    const data = {
        idCliente: 2,
        items: obtenerParesProductoCantidadSS(),
    };
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    const response = await fetch('http://localhost:8081/carritos', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: obtenerTokenAuthorizationHeader(headers),
    });
    switch (response.status) {
        case 201:
            alert('Carrito creado.');
            vaciarCarrito();
            window.location.replace('./index.html');
            break;
        case 400:
            alert('Error 404 al crear carrito.');
            break;
        default:
            alert('Error 500 al crear carrito.');
            break;
    }
};

verCarrito();
