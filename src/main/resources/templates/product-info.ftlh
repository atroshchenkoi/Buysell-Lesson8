<#import "blocks/template.ftlh" as t>
<@t.template user>
    <div id="carouselExampleDark" class="carousel carousel-dark slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <#list images as image>
                <div class="carousel-item <#if image.previewImage>active</#if>">
                    <img src="/images/${image.getId()}" class="d-block w-100" width="800px" height="600px" alt="...">
                </div>
            </#list>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <hr>
    <p><h1 style="text-align: center">${product.title} | ${product.price} BYN</h1></p>
    <div class="row justify-content-center">
        <div class="col-8">
            <p style="text-align: center; word-wrap: break-word;" >
                ${product.description}
            </p>
        </div>
    </div>
    <p style="text-align: center">Номер телефона продавца: <b>${authorProduct.phoneNumber}</b></p>
    <hr>
    <p style="text-align: center">
        <#if authorProduct.getId() == user.getId()>
            <a href="/profile">
                <img src="/static/images/avatar.png" alt="mdo" width="32" height="32" class="rounded-circle"> ${authorProduct.name}
            </a>
            <#else>
                <a href="/user/${authorProduct.id}">
                    <img src="/static/images/avatar.png" alt="mdo" width="32" height="32" class="rounded-circle"> ${authorProduct.name}
                </a>
        </#if>
    </p>

</@t.template>