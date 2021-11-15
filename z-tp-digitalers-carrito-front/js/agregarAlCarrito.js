const listarProductos = () => {
    tokenPresente();
    fetch('http://localhost:8081/productos', {
        headers: obtenerTokenAuthorizationHeader(new Headers()),
    })
        .then((resp) => {
            // if (!resp.ok) {
            //     throw Error('Ha ocurrido un problema al intentar traer información sobre los productos.');
            // }
            return resp.json();
        })
        .then((data) => {
            if (!tokenValido(data)) {
                return false;
            }
            if (data.status === 200) {
                if (data.elementos.length > 0) {
                    document.querySelector('#msj').innerHTML = '';
                    data.elementos.forEach((producto) => {
                        document.querySelector('#lista-productos div').appendChild(crearCard(producto));
                    });
                } else {
                    eliminarTodasLasCards();
                }
            } else {
                document.querySelector('#lista-productos tr').remove();
                alert(data.status + ' ' + data.mensaje);
            }
        });
};

const crearCard = (producto) => {
    const col = document.createElement('div');
    col.classList.add('col-4');
    col.classList.add('d-flex');
    col.classList.add('justify-content-center');
    col.classList.add('producto');
    col.innerHTML = `
                <div class='card mb-4'>
                    <img src='http://localhost:8081/img/${producto.nombreImagen}' class='card-img-top' alt='${producto.descripcion}' />
                    <div class='card-body text-center'>
                        <p class='h5 card-title descripcion' style="height: 50px;">${producto.descripcion}</p>
                        <p class="cantidad" data-cantmax="${producto.stock}">Cantidad: <input type="number" min="1" max="${producto.stock}" value="1" style="margin-left: 20px;"/> de ${
        producto.stock
    }</p>
                        <p class='card-text precio' data-precio="${producto.precio}">${formatearMoneda(producto.precio)}</p>
                        <button class='btn btn-primary btn-agregar' data-id="${producto.id}">Agregar</button>
                        <button class='btn btn-danger btn-eliminar' data-id="${producto.id}">Eliminar</button>
                        <button class='btn btn-secondary btn-editar' data-id="${producto.id}">Editar</button>
                    </div>
                </div>`;
    return col;
};

const eliminarTodasLasCards = () => {
    if (document.querySelector('#lista-productos .producto')) {
        document.querySelector('#lista-productos .producto').remove();
    }
    document.querySelector('#msj').innerHTML = 'No hay productos disponibles.';
};

document.querySelector('#lista-productos div').addEventListener('click', (infoEvento) => {
    if (infoEvento.target.classList.contains('btn-agregar')) {
        const card = infoEvento.target.parentElement.parentElement;
        agregarAlCarrito(card);
    }

    if (infoEvento.target.classList.contains('btn-eliminar')) {
        eliminarProducto(infoEvento.target.dataset.id);
    }

    if (infoEvento.target.classList.contains('btn-editar')) {
        window.location.replace(`./nuevo-producto.html?id=${infoEvento.target.dataset.id}`);
    }
});

const agregarAlCarrito = (card) => {
    const productoParaElCarrito = {
        id: card.querySelector('.btn-agregar').dataset.id,
        descripcion: card.querySelector('.descripcion').textContent,
        precio: Number(card.querySelector('.precio').dataset.precio),
        URLImagen: card.querySelector('img').src,
        cantidad: card.querySelector('.cantidad input').value,
        stock: card.querySelector('.cantidad').dataset.cantmax,
    };
    let productosCarritoTemp = obtenerCarritoSS();
    const indiceProductoActual = productosCarritoTemp.findIndex((elemento) => productoParaElCarrito.id === elemento.id);
    if (indiceProductoActual > -1) {
        productosCarritoTemp[indiceProductoActual] = productoParaElCarrito;
    } else {
        productosCarritoTemp.push(productoParaElCarrito);
    }
    guardarEnCarritoSS(productosCarritoTemp);
    reiniciarCantidades();
    alert(`El producto "${productoParaElCarrito.descripcion}" ha sido agregado al carrito.`);
};

const reiniciarCantidades = () => {
    document.querySelectorAll('#lista-productos .card input').forEach((inputCantidad) => {
        inputCantidad.value = 1;
    });
};

const eliminarProducto = (idProducto) => {
    fetch(`http://localhost:8081/productos/${idProducto}`, { method: 'DELETE' })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            alert(data.mensaje);
            document.querySelector('#lista-productos tr').remove();
            listarProductos();
            // return;
            // if (data.status != 200) {
            //     alert(data.mensaje);
            //     return;
            // }
            // txtId.value = data.elemento.id;
            // txtDescripcion.value = data.elemento.descripcion;
            // txtPrecio.value = data.elemento.precio;
            // txtStock.value = data.elemento.stock;
        });
};

listarProductos();
