import React from 'react';
import './styles.css';
import emporio from '../../assets/emporio.jpg'

export default function Login(){
    return(
        <div className="login-container">
            <section className="form">
                <form>
                    <img src={emporio} alt="Login"/>
                    <h1>Entre na sua conta</h1>
                    <input placeholder="Username"/>
                    <input type="password" placeholder="Password"/>
                    <button className="button" type="submit">Entrar</button>
                </form>
            </section>
           
        </div>
    );
}