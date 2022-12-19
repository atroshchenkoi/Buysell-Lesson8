<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h1 style="text-align: center">Информация о пользователе</h1>
    <hr>
    <div style="text-align: center">
            <img src="/static/images/avatar.png"
                 style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${user.name}</h2>
        <br>
        Number: <b>${user.phoneNumber}</b>
        <br>
        Mail: <b>${user.email}</b>
    </div>
    <br>
    <hr>
    <br>
    <#if userByPrincipal.isAdmin()>
        <div class="d-flex flex-wrap  justify-content-lg-start">
            <div class="col-6">
                <h2 style="text-align: center">Deposits ${user.name}</h2>
                <#list deposits as deposit>
                    <div class="list-group w-auto" style="height: 6rem";>
                        <div class="d-flex gap-2 w-100 justify-content-between" >
                            <div>
                                <h6 class="mb-0">Deposit via card: ${deposit.getCardNumber()}</h6>
                                <p class="mb-0 opacity-75">Replenishment in the amount of ${deposit.getSum()} BYN</p>
                                <small class="opacity-50 text-nowrap">${deposit.getDateOfCreated()}</small>
                            </div>
                        </div>
                    </div>
                    <hr>
                </#list>
            </div>
            <div class="col-6" style="padding-left: 10px;">
                <h2 style="text-align: center">Payments ${user.name}</h2>
                <#list payments as payment>
                    <div class="list-group w-auto" style="height: 6rem;">
                        <div class="d-flex gap-2 w-100 justify-content-between">
                            <div>
                                <h6 class="mb-0">Payment for product billing: ${payment.getProduct().getTitle()}</h6>
                                <p class="mb-0 opacity-75">Payment amount -${payment.getSum()} BYN</p>
                                <small class="opacity-50 text-nowrap">${payment.getDateOfCreated()}</small>
                            </div>
                        </div>
                    </div>
                    <hr>
                </#list>
            </div>
        </div>
    </#if>
    <h2 style="text-align: center">Products ${user.name}</h2>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <#list products as product>
            <div class="card text-white bg-dark my-3 product" style="margin-left: 2rem;">
                <div class="card-top">
                    <#if userByPrincipal.isAdmin()>
                        <form action="/admin/product/delete/${product.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="hidden" value="${product.getUser().getId()}" name="userId">
                            <button type="submit" class="btn btn-danger m-3">
                                Delete
                            </button>
                        </form>
                    </#if>
                </div>
                <img src="/images/${product.previewImageId}"
                     class="card-img-top">
                <div class="card-body">
                    <h5 class="card-title">${product.title}</h5>
                    <p class="card-text">${product.price} ₽</p>
                </div>
                <div class="card-footer">
                    ${product.city}
                </div>
            </div>
        </#list>
    </div>
    </div>
</@t.template>