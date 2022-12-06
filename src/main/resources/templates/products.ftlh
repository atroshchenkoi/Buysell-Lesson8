<#import "blocks/template.ftlh" as t>
<@t.template user>
    <div class="row">
    <!--ALBUM START-->
        <div class="col-9">
            <div class="album py-5 ">
                <div class="container">

                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                        <#list products as product>
                        <div class="col">
                            <div class="card shadow-sm">
                                <img src="images/${product.previewImageId}" class="bd-placeholder-img card-img-top" width="100%" height="225">
                                <div class="card-body">
                                    <p class="card-text" _msthash="1937507" _msttexthash="121948749">${product.getTitle()}</p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="window.location.href = '/product/${product.getId()}/buy'" _msthash="3235427" _msttexthash="333034">Купить</button>
                                            <button type="button" class="btn btn-sm btn-outline-secondary" onclick="window.location.href = '/product/${product.getId()}'" _msthash="3235609" _msttexthash="2377492">Информация</button>
                                        </div>
                                        <small class="text-muted" _msthash="2661984" _msttexthash="385333">9 мин</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </a>
                        </#list>

                    </div>
                </div>
            </div>
        </div>
        <div class="col-3">
            <div class="list-group mx-0 w-auto">
                <span class="fs-4 fw-semibold text-center">Types</span>
                <#list types as type>
                    <label class="list-group-item d-flex gap-2">
                        <input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="">
                        <span>${type}</span>
                    </label>
                </#list>
                <span class="fs-4 fw-semibold text-center">Cities</span>
                <#list cities as city>
                    <label class="list-group-item d-flex gap-2">
                        <input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="">
                        <span>${city}</span>
                    </label>
                </#list>
                <span class="fs-4 fw-semibold text-center">Conditions</span>
                <#list health as health>
                    <label class="list-group-item d-flex gap-2">
                        <input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="">
                        <span>${health}</span>
                    </label>
                </#list>
            </div>
        </div>
    </div>

</@t.template>