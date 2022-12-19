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
                            <div class="card" style="height: 24rem";>
                                <img src="images/${product.previewImageId}" class="bd-placeholder-img card-img-top" width="100%" height="225">
                                <div class="card-body">
                                    <p class="card-text" _msthash="1937507" _msttexthash="121948749">${product.getTitle()}</p>
                                    <div class="d-flex justify-content-between align-items-center" style="padding-bottom: 0;">
                                        <button type="button" class="btn btn-sm btn-outline-secondary" style="margin-bottom: 0" onclick="window.location.href = '/product/${product.getId()}'" _msthash="3235609" _msttexthash="2377492">Info</button>
                                        <small class="text-muted" _msthash="2661984" _msttexthash="385333">${product.getDateOfCreated()}</small>
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
            <form action="/" method="get">
                <div class="list-group mx-0 w-auto">
                    <span class="fs-4 fw-semibold text-center">Types</span>
                    <#list types as type>
                        <label class="list-group-item d-flex gap-2">
                            <input class="form-check-input flex-shrink-0"  type="checkbox" name="${type}" value="${type}">
                            <span>${type}</span>
                        </label>
                    </#list>
                    <span class="fs-4 fw-semibold text-center">Cities</span>
                    <#list cities as city>
                        <label class="list-group-item d-flex gap-2">
                            <input class="form-check-input flex-shrink-0" type="checkbox" name="${city}" value="${city}">
                            <span>${city}</span>
                        </label>
                    </#list>
                    <span class="fs-4 fw-semibold text-center">Conditions</span>
                    <#list health as health>
                        <label class="list-group-item d-flex gap-2">
                            <input class="form-check-input flex-shrink-0"  type="checkbox" name="${health}" value="${health}">
                            <span>${health}</span>
                        </label>
                    </#list>
                </div>

                <hr>
                <button type="submit" style="width: 100%" class="btn btn-warning">Select</button>
            </form>
        </div>
    </div>

</@t.template>