<#import "blocks/template.ftlh" as t>
<@t.template user>

    <hr>
    <form action="/registration" method="post">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" minlength="4" maxlength="30" required id="name" name="name">
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" required name="email">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Telephone number</label>
            <input type="text" class="form-control" pattern="\+375[0-9]{9}" placeholder="+375(**)*******" id="phoneNumber" required name="phoneNumber">

        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" minlength="7" id="password" required name="password">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <hr>
        <button type="submit" style="width: 100%" class="btn btn-warning">Registration</button>
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</@t.template>