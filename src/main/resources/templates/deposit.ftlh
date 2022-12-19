<#import "blocks/template.ftlh" as t>
<@t.template user>
    <form action="/user/cashAccount/deposit" method="post">
        <div class="form-group">
            <label for="number">Card number</label>
            <input type="text" pattern="[0-9]{16}" placeholder="16 NUMBERS FORMAT" class="form-control" required id="number" name="number">
        </div>
        <div class="form-group">
            <label for="dateExpire">Date expire</label>
            <input type="text" class="form-control" pattern="[0-9]{2}\/[0-9]{2}" placeholder="**/** FORMAT" id="dateExpire" required name="dateExpire">
        </div>
        <div class="form-group">
            <label for="CVVCode">CVV</label>
            <input type="text" class="form-control" pattern="[0-9]{3}" placeholder="*** FORMAT" id="CVVCode" required name="CVVCode">
        </div>
        <div class="form-group">
            <label for="sum">SUM</label>
            <input type="number" class="form-control" pattern="\d+(\.\d{2})?" step="0.01" id="sum" required name="sum">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <hr>
        <button type="submit" style="width: 100%" class="btn btn-warning">Deposit</button>
    </form>

</@t.template>