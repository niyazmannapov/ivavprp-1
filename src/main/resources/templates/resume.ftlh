<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Резюме</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/resume.css">
</head>
<body>

<header>
    <div class="d-flex flex-column flex-md-row align-items-center px-md-4">
        <a href="#" class="navbar-brand my-0 mr-md-auto"><img src="../img/logo.png" alt="logo"></a>
        <b class="btn btn-outline-info" onclick="logout()">Выход</b>
    </div>
</header>
<main>
    <div class="container-fluid">
        <div class="row justify-content-center text-center">
            <div class="col-sm-10 profile">
                <div class="row text-center justify-content-center">
                    <h1 id="name" class="mt-2"></h1>
                </div>
                <div class="row">
                    <p id="text-about" class="offset-1 col-md-10 mt-4 text-justify"></p>
                </div>
                <div class="row offset-1">
                    <p class="font-weight-bold">Тип трудоустройства: </p>
                    <p id="employment-type"></p>
                </div>
                <div class="row offset-1">
                    <p class="font-weight-bold">Занятость: </p>
                    <p id="work-schedule"></p>
                </div>
                <div class="row justify-content-center">
                    <a class="btn btn-info" id="student-profile"></a>
                </div>
                <div class="row offset-1" id="skills-list">
                    <p>Список компетенций: </p>
                </div>
            </div>
        </div>
    </div>
    </div>
</main>
<script>
    window.onload = function () {
        let token = localStorage.getItem("token");
        if (token == null) {
            logout()
        }
        let request = new XMLHttpRequest();
        let id = document.location.pathname.slice(8);


        request.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                let resume = JSON.parse(this.responseText);
                document.getElementById("employment-type").innerHTML = resume.emplTypeToShow;
                document.getElementById("work-schedule").innerHTML = resume.workScheduleToShow;
                document.getElementById("text-about").innerHTML = resume.text;
                document.getElementById("name").innerHTML = resume.name;
                let stud_url = document.getElementById("student-profile");
                stud_url.href = "/students/" + resume.studentId;
                stud_url.innerHTML = "Перейти к студенту";
                let skills_list = document.getElementById("skills-list");
                for (let i = 0; i < resume.skills.length; i++) {
                    let skill = document.createElement("p");
                    skill.innerHTML = resume.skills[i].name + "&nbsp";
                    skills_list.appendChild(skill)
                }
            }
        };

        request.open("GET", "/restApi/resume/" + id);
        request.setRequestHeader('AUTH', localStorage.getItem("token"));
        request.send();
    };
</script>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>

</body>
</html>