const listarProductos = () => {
    fetch('http://localhost:8081/productos')
        .then((resp) => {
            // if (!resp.ok) {
            //     throw Error('Ha ocurrido un problema al intentar traer información sobre los productos.');
            // }
            return resp.json();
        })
        .then((data) => {
            if (data.status === 200) {
                const table = document.querySelector('#lista-productos');
                if (data.elementos.length > 0) {
                    // table.innerHTML = `<tr class="cabecera">
                    //     <th></th>
                    //     <th>Descripción</th>
                    //     <th>Precio</th>
                    //     <th>Cantidad</th>
                    //     <th>Acciones</th>
                    // </tr>`;
                    data.elementos.forEach((producto) => {
                        document.querySelector('#lista-productos div').appendChild(crearCard(producto));
                        //     table.innerHTML += `<tr>
                        //     <td><img src="http://localhost:8081/img/${producto.nombreImagen}" alt="${producto.descripcion}"></td>
                        //     <td>${producto.descripcion}</td>
                        //     <td>${producto.precio}</td>
                        //     <td><input type="number" min="1" max="10" value="1"></td>
                        //     <td><button type="button" class="agregar-carrito" id="${producto.id}">Agregar al carrito</button></td>
                        // </tr>`;
                    });
                    document.querySelectorAll('.agregar-carrito').forEach((boton) => {
                        boton.addEventListener('click', () => {
                            const idProducto = boton.getAttribute('id');
                            const cantidad = boton.parentElement.parentElement.cells[2].firstChild.value;
                            let carrito = JSON.parse(sessionStorage.getItem('carrito')) || [];
                            const indiceProductoActual = carrito.findIndex((producto) => producto.idProducto === idProducto);
                            if (indiceProductoActual > -1) {
                                carrito[indiceProductoActual] = { idProducto: idProducto, cantidad: cantidad };
                            } else {
                                carrito.push({ idProducto: idProducto, cantidad: cantidad });
                            }
                            sessionStorage.setItem('carrito', JSON.stringify(carrito));
                            const producto = boton.parentElement.parentElement.cells[0].firstChild.textContent;
                            alert(`El producto "${producto}" ha sido agregado al carrito.`);
                        });
                    });
                } else {
                    document.querySelector('#lista-productos tr').remove();
                    const parrafo = document.createElement('p');
                    parrafo.innerHTML = 'No hay productos disponibles.';
                    document.body.appendChild(parrafo);
                }

                //     data.entidades.forEach((element) => {
                //         table.innerHTML += `
                //     <tr class="cabecera">
                //         <td>${element.tipoDocumento}</td>
                //         <td>${element.numeroDocumento}</td>
                //         <td>${element.nombre}</td>
                //         <td>${element.apellido}</td>
                //         <td>${element.fechaNacimiento.day}/${element.fechaNacimiento.month}/${element.fechaNacimiento.year}</td>
                //         <td>${element.sexo}</td>
                //         <td>${element.direccion ? element.direccion : '-'}</td>
                //         <td>${element.telefono ? element.telefono : '-'}</td>
                //         <td>${element.ocupacion ? element.ocupacion : '-'}</td>
                //         <td>${element.ingresoMensual}</td>
                //         <td><button type="button" class="editar" id="${element.id}">Editar</button><button type="button" class="borrar" id="${element.id}">Eliminar</button></td>
                //     </tr>`;
                //     });
                //     this._shadowRoot.querySelectorAll('.borrar').forEach((boton) => {
                //         boton.addEventListener('click', () => {
                //             const idElemento = boton.getAttribute('id');
                //             fetch('http://localhost:8080/Censo2021OK/BorrarPersona?id=' + idElemento)
                //                 .then((resp) => resp.json())
                //                 .then((data) => {
                //                     switch (data.status) {
                //                         case 200:
                //                             window.location.replace('http://127.0.0.1:5500/operacionExitosa.html');
                //                             break;
                //                         case 500:
                //                             alert(data.mensaje);
                //                             break;
                //                     }
                //                 });
                //         });
                //     });
                //     this._shadowRoot.querySelectorAll('.editar').forEach((boton) => {
                //         boton.addEventListener('click', () => {
                //             const idElemento = boton.getAttribute('id');
                //             window.location.replace('http://127.0.0.1:5500/index.html?id=' + idElemento);
                //         });
                //     });
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
    col.innerHTML = `
                <div class='card mb-4'>
                    <img src='http://localhost:8081/img/${producto.nombreImagen}' class='card-img-top' alt='${producto.descripcion}' />
                    <div class='card-body text-center'>
                        <h5 class='card-title descripcion' style="height: 50px;">${producto.descripcion}</h5>
                        <p class="cantidad">Cantidad: <input type="number" min="1" max="${producto.stock}" value="1" style="margin-left: 20px;"/></p>
                        <p class='card-text precio' data-precio="${producto.precio}">${formatearMoneda(producto.precio)}</p>
                        <button class='btn btn-dark btn-agregar' data-id="${producto.id}">Agregar</button>
                    </div>
            </div>`;
    return col;
};

document.querySelector('#lista-productos div').addEventListener('click', (infoEvento) => {
    if (infoEvento.target.classList.contains('btn-agregar')) {
        const card = infoEvento.target.parentElement.parentElement;
        agregarAlCarrito(card);
    }
});

const agregarAlCarrito = (card) => {
    const productoParaElCarrito = {
        id: card.querySelector('.btn-agregar').dataset.id,
        descripcion: card.querySelector('.descripcion').textContent,
        precio: Number(card.querySelector('.precio').dataset.precio),
        URLImagen: card.querySelector('img').src,
        cantidad: card.querySelector('.cantidad input').value,
    };
    let productosCarritoTemp = obtenerCarritoSS();
    const indiceProductoActual = productosCarritoTemp.findIndex((elemento) => productoParaElCarrito.id === elemento.id);
    if (indiceProductoActual > -1) {
        productosCarritoTemp[indiceProductoActual] = productoParaElCarrito;
    } else {
        productosCarritoTemp.push(productoParaElCarrito);
    }
    sessionStorage.setItem('productosCarritoTemp', JSON.stringify(productosCarritoTemp));
    reiniciarCantidades();
    alert(`El producto "${productoParaElCarrito.descripcion}" ha sido agregado al carrito.`);
};

const reiniciarCantidades = () => {
    document.querySelectorAll('#lista-productos .card input').forEach((inputCantidad) => {
        inputCantidad.value = 1;
    });
};

listarProductos();
